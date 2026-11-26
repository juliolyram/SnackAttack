package br.com.snackattack;

import br.com.snackattack.dominio.Barras;
import br.com.snackattack.dominio.Jogador;
import br.com.snackattack.dominio.Mapa;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Mundo {
    private Jogador jogador;
    private World mundo;
    private Box2DDebugRenderer debugRenderer;
    private Mapa mapa;
    private Barras barras;

    public Mundo() {
        Box2D.init();
        mundo = new World(new Vector2(0, -22), true);
        debugRenderer = new Box2DDebugRenderer();
        jogador = new Jogador(mundo);
        mapa = new Mapa(mundo);
        barras = new Barras();
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
        mundo.step(1 / 60f, 6, 2);
        jogador.animar(delta);
    }

    public void renderMapa(OrthographicCamera camera) {
        mapa.render(camera);
    }

    public void renderJogador(SpriteBatch batch) {
        jogador.render(batch);
    }

    public void renderBarras(){
        barras.render(jogador);
    }

    public void redimensionar(int largura, int altura) {
        barras.redimensionar(largura, altura);
    }

    public void renderDebug(Matrix4 camera) {
        debugRenderer.render(mundo, camera);
    }

    //dados que o Main usa para posicionar a câmera
    public float getJogadorX() {
        return jogador.getX();
    }

    public float getJogadorY() {
        return jogador.getY();
    }

    public int getMapaLargura() {
        return mapa.largura;
    }

    public int getMapaAltura() {
        return mapa.altura;
    }

    public Jogador getJogador() {
        return jogador;
    }
    //fim

    public void dispose() {
        jogador.dispose();
        mapa.dispose();
        debugRenderer.dispose();
        mundo.dispose();
        barras.dispose();
    }
}
