
package br.com.snackattack;

import br.com.snackattack.dominio.Jogador;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Mundo {
    private Jogador jogador;
    private World mundo;
    private Box2DDebugRenderer debugRenderer;
    public Mundo() {
        Box2D.init();
        mundo = new World(new Vector2(0,-22),true);
        debugRenderer  = new Box2DDebugRenderer();
        jogador = new Jogador(mundo);
        mundo.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contato) {
                Fixture fixtureA = contato.getFixtureA();
                Fixture fixtureB = contato.getFixtureB();

                if ("pe".equals(fixtureA.getUserData()) ||
                    "pe".equals(fixtureB.getUserData())) {

                    jogador.adicionarContatoPe();
                }
            }

            @Override
            public void endContact(Contact contato) {
                Fixture fixtureA = contato.getFixtureA();
                Fixture fixtureB = contato.getFixtureB();

                if ("pe".equals(fixtureA.getUserData()) ||
                    "pe".equals(fixtureB.getUserData())) {

                    jogador.removerContatoPe();
                }
            }

            @Override
            public void preSolve(Contact contato, Manifold manifold) {
            }

            @Override
            public void postSolve(Contact contato, ContactImpulse impulso) {
            }
        });
        chao();

    }
    public void chao(){
        BodyDef confChao = new BodyDef();
        confChao.type = BodyDef.BodyType.StaticBody;
        confChao.position.set(8,0);

        Body chao = mundo.createBody(confChao);
        PolygonShape chaoHb = new PolygonShape(); // Criando a HitBox do chão
        chaoHb.setAsBox(8f, 0.5f); // depois criar um paramentro para receber a largura e alturar
        // OBS: LEMBRAR QUE É APEANS A MÉTADE DO QUE É PARA SER
        chao.createFixture(chaoHb, 0f);
        chaoHb.dispose();
    }

    public void update(float delta, float mundoLargura, float mundoAltura) {
        jogador.lerEntrada();
        jogador.mover(delta);
        mundo.step(1/60f,6,2);
        jogador.animar(delta);
    }

    public void render(SpriteBatch batch, float largura, float altura) {
        jogador.render(batch);
    }
    public void renderDebug(Matrix4 camera) {
        debugRenderer.render(mundo, camera);
    }
    public void dispose() {
        jogador.dispose();
        debugRenderer.dispose();
        mundo.dispose();
    }
}
