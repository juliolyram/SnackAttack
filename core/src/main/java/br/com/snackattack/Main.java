package br.com.snackattack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils; // ===== NOVO ===== (import para o clamp)
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Main extends Game {
    private SpriteBatch batch;
    private Mundo mundo;
    public ExtendViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new ExtendViewport(16f, 9f);
        mundo = new Mundo();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        mundo.update(delta, viewport.getWorldWidth(), viewport.getWorldHeight());

        ScreenUtils.clear(Color.BLACK);
        viewport.apply(false);
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();

        float metadeL = viewport.getWorldWidth() / 2f;
        float metadeA = viewport.getWorldHeight() / 1.5f;

        camera.position.x = MathUtils.clamp(mundo.getJogadorX(), metadeL, mundo.getMapaLargura() - metadeL);
        camera.position.y = MathUtils.clamp(mundo.getJogadorY(), metadeA, mundo.getMapaAltura() - metadeA);
        camera.update();

        System.out.println("jogador=" + mundo.getJogadorX() + "," + mundo.getJogadorY()
            + " camera=" + camera.position.x + "," + camera.position.y
            + " mapa=" + mundo.getMapaLargura() + "x" + mundo.getMapaAltura());
        mundo.renderMapa(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        mundo.renderJogador(batch);
        batch.end();
        mundo.renderBarras();

        mundo.renderDebug(camera.combined);
    }

    @Override
    public void resize(int largura, int altura) {
        viewport.update(largura, altura, false);
        mundo.redimensionar(largura, altura);
    }

    @Override
    public void dispose() {
        batch.dispose();
        mundo.dispose();
    }
}
