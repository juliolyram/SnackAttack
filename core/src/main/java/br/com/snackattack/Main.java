package br.com.snackattack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image, tNave;
    public Sprite nave;
    private Jogador jogador;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        tNave = new Texture("Boneco NORMAL A1.png");
        nave = new Sprite(tNave);
        jogador = new Jogador(nave);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        jogador.moverJogador(delta);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.draw(nave, jogador.getPosX(), jogador.getPosY());
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        tNave.dispose();
    }
}
