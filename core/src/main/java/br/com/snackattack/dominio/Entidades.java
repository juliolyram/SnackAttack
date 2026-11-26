package br.com.snackattack.dominio;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entidades {
    protected Body corpo;
    protected float vidaMaxima;
    protected float vida;
    protected float dano;

    private float interInv = 0f; // invencivel
    private final float DUR_INV = 1f; //tempo até acabar

    protected Entidades(World mundo, float x, float y, float vidaMaxima, float dano){
        this.vidaMaxima = vidaMaxima;
        this.vida = vida;
        this.dano = dano;

        BodyDef confCorporal = new BodyDef();
        confCorporal.type = BodyDef.BodyType.DynamicBody;
        confCorporal.position.set(2f, 4f);
        confCorporal.fixedRotation = true;

        corpo = mundo.createBody(confCorporal);
    }

    public void receberDano(float quantidade){
        if(interInv > 0f){
            vida = Math.min(0f, vida + quantidade);
            interInv = DUR_INV;
        }
    }
    public void curar (float quantidade){
        vida = Math.min(vidaMaxima, vida + quantidade);
    }
    protected  void atualizar(float delta){
        if(interInv > 0f) interInv -= delta;
    }
    public boolean estaVivo() {
        return vida > 0f;
    }

    public float getVidaMaxima() {
        return vidaMaxima;
    }

    public float getVida() {
        return vida;
    }

    public float getDano() {
        return dano;
    }

    public float getX() {
        return corpo.getPosition().x;
    }

    public float getY() {
        return corpo.getPosition().y;
    }
}
