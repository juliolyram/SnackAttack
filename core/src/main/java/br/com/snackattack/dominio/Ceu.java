package br.com.snackattack.dominio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Ceu {
    private Texture fundo,vinheta, nuvens;
    public Ceu(World mundo) {
        fundo = new Texture("background.png");
        vinheta = new Texture("vignette.png");
        nuvens = new Texture("clouds.png");
    }

    public void render(SpriteBatch batch, float largura, float altura ){
        batch.draw(fundo,0,0,largura, altura);
        batch.draw(vinheta,0,0,largura, altura);
        batch.draw(nuvens,0,0,largura, altura);
    }

    public void dispose(){
        fundo.dispose();
        vinheta.dispose();
        nuvens.dispose();
    }
}


