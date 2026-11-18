
package br.com.snackattack.dominio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Chao {

    private Texture textura;

    public Chao(World mundo) {

        textura = new Texture("ground.png");

        BodyDef configuracaoChao = new BodyDef();
        configuracaoChao.type = BodyDef.BodyType.StaticBody;
        configuracaoChao.position.set(8f, 0.6f);

        Body corpoChao = mundo.createBody(configuracaoChao);

        PolygonShape formaChao = new PolygonShape();
        formaChao.setAsBox(8f, 0.5f);

        Fixture fixtureChao = corpoChao.createFixture(formaChao, 0f);
        fixtureChao.setUserData("solo");

        formaChao.dispose();
    }

    public void render(SpriteBatch batch, float largura, float altura) {
        batch.draw(textura, 0, 0, largura, altura);
    }

    public void dispose() {
        textura.dispose();
    }
}
