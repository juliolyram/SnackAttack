package br.com.snackattack.dominio;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
// revisar
public class Barras {
    private ShapeRenderer forma = new ShapeRenderer();
    private OrthographicCamera camera = new OrthographicCamera();

    public void redimensionar(int largura, int altura) {
        camera.setToOrtho(false, largura, altura);
    }

    public void render(Jogador j) {
        camera.update();
        forma.setProjectionMatrix(camera.combined);
        forma.begin(ShapeRenderer.ShapeType.Filled);

        desenharBarra(20, camera.viewportHeight - 40, 200, 16, j.getVida() / j.getVidaMaxima(), Color.RED);
        desenharBarra(20, camera.viewportHeight - 64, 200, 12, j.getEstomago() / j.getESTOMAGO_MAX(), Color.BROWN);

        forma.end();
    }

    private void desenharBarra(float x, float y, float larg, float alt, float proporcao, Color cor) {
        forma.setColor(Color.DARK_GRAY);
        forma.rect(x, y, larg, alt);
        forma.setColor(cor);
        forma.rect(x, y, larg * proporcao, alt);
    }

    public void dispose() { forma.dispose(); }
}
