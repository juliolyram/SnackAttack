package br.com.snackattack;

import br.com.snackattack.dominio.Jogador;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class Main extends Game {
    private SpriteBatch batch;
    private Texture image;
    private Mundo mundo;
    public ExtendViewport viewport;


    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new ExtendViewport(16f,9f);
        mundo = new Mundo();
    }
    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        float largura = viewport.getWorldWidth();
        float altura = viewport.getWorldHeight();

        mundo.update(delta, largura, altura);

        ScreenUtils.clear(Color.BLACK);

        viewport.apply(true);
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        mundo.render(batch, largura, altura);
        batch.end();

        mundo.renderDebug(viewport.getCamera().combined);
    }
    @Override
    public void resize(int largura, int altura){
        viewport.update(largura, altura, true);
    }
    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        mundo.dispose();
    }
}
