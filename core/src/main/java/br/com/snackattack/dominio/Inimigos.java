package br.com.snackattack.dominio;

import com.badlogic.gdx.physics.box2d.World;

public class Inimigos extends Entidades{
    public Inimigos(World mundo, float x, float y){
        super(mundo, x, y, 30, 20);

        criarForma();
    }

    private void criarForma() {
        
    }
}
