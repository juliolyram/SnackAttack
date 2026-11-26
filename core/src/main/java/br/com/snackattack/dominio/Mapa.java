package br.com.snackattack.dominio;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;

public class Mapa {
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderer;
    public final int largura, altura; // em tiles

    public Mapa(World mundo) {
        mapa = new TmxMapLoader().load("mapa.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa, 1 / 16f);
        TiledMapTileLayer camada = (TiledMapTileLayer) mapa.getLayers().get("chao");
        largura = camada.getWidth();
        altura = camada.getHeight();

        criarColisao(mundo, camada);
    }

// construção com a ajuda de IA
    private void criarColisao(World mundo, TiledMapTileLayer camada) {
        BodyDef conf = new BodyDef();
        conf.type = BodyDef.BodyType.StaticBody;
        Body corpo = mundo.createBody(conf);


        PolygonShape forma = new PolygonShape();
        for (int x = 0; x < camada.getWidth(); x++) {
            for (int y = 0; y < camada.getHeight(); y++) {
                // incompleto, serve para transformar em rampas
                TiledMapTileLayer.Cell celula = camada.getCell(x, y);
                if (camada.getCell(x, y) != null) {
                    forma.setAsBox(0.5f, 0.5f, new com.badlogic.gdx.math.Vector2(x + 0.5f, y + 0.5f), 0);
                    Fixture coliso = corpo.createFixture(forma, 0f);
                    coliso.setUserData("solo");
                }
            }
        }

        forma.dispose();
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose() {
        mapa.dispose();
        renderer.dispose();
    }
}
