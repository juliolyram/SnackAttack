
package br.com.snackattack;

import br.com.snackattack.dominio.Jogador;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mundo {
    private Jogador jogador;

    public Mundo() {
        jogador = new Jogador();
    }

    public void update(float delta, float mundoLargura, float mundoAltura) {
        jogador.mover(delta, mundoLargura, mundoAltura);
    }

    public void render(SpriteBatch batch, float largura, float altura) {
        jogador.render(batch);
    }

    public void dispose() {
        jogador.dispose();
    }
}
