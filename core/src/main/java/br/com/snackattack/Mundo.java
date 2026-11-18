
package br.com.snackattack;

import br.com.snackattack.dominio.Ceu;
import br.com.snackattack.dominio.Chao;
import br.com.snackattack.dominio.Jogador;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Mundo {
    private Jogador jogador;
    private World mundo;
    private Box2DDebugRenderer debugRenderer;
    private Chao chao;
    private Ceu ceu;
    public Mundo() {
        Box2D.init();
        mundo = new World(new Vector2(0,-22),true);
        debugRenderer  = new Box2DDebugRenderer();
        jogador = new Jogador(mundo);
        chao = new Chao(mundo);
        ceu = new Ceu(mundo);
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

    }

    public void update(float delta, float mundoLargura, float mundoAltura) {
        jogador.lerEntrada();
        jogador.mover(delta);
        mundo.step(1/60f,6,2);
        jogador.animar(delta);
    }

    public void render(SpriteBatch batch, float largura, float altura) {
        ceu.render(batch, largura, altura);
        chao.render(batch, largura,altura);
        jogador.render(batch);
    }
    public void renderDebug(Matrix4 camera) {
        debugRenderer.render(mundo, camera);
    }
    public void dispose() {
        jogador.dispose();
        chao.dispose();
        ceu.dispose();
        debugRenderer.dispose();
        mundo.dispose();
    }
}
