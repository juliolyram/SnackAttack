package br.com.snackattack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Jogador {
    public Sprite prota;
    private float posX = 0, posY = 0;
    private float VELOCIDADE = 300;


    public Jogador(Sprite prota) {
        this.prota = prota;
    }
    public void moverJogador(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            if (posX < Gdx.graphics.getWidth() - prota.getWidth()) {
                posX += VELOCIDADE * delta;
            }
        } else if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
            if (posY < Gdx.graphics.getHeight() - prota.getHeight()){
                posY += VELOCIDADE * delta;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (Gdx.graphics.getWidth() > 0){
                posX -= VELOCIDADE * delta;
            }
            }
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
}
