
package br.com.snackattack;

import br.com.snackattack.dominio.Jogador;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Mundo {
    private Jogador jogador;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    public Mundo() {
        Box2D.init();
        world = new World(new Vector2(0,-10),true);
        debugRenderer  = new Box2DDebugRenderer();
        jogador = new Jogador(world);


    }

    public void update(float delta, float mundoLargura, float mundoAltura) {
        jogador.mover(delta, mundoLargura, mundoAltura);
        world.step(1/60f,6,2);
    }

    public void render(SpriteBatch batch, float largura, float altura) {
        jogador.render(batch);
    }
    public void renderDebug(Matrix4 camera) {
        debugRenderer.render(world, camera);
    }
    public void dispose() {
        jogador.dispose();
        debugRenderer.dispose();
        world.dispose();
    }
}
