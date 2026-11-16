package br.com.snackattack;

import br.com.snackattack.dominio.Jogador;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;


public class Main extends Game {
    private SpriteBatch batch;
    private Texture image;
    private Mundo mundo;


    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        mundo = new Mundo();
    }
    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        float largura = Gdx.graphics.getWidth();
        float altura = Gdx.graphics.getHeight();

        mundo.update(delta, largura, altura);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        
        batch.begin();
        batch.draw(image, 140, 210);
        mundo.render(batch, largura, altura);
        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        mundo.dispose();
    }
}
