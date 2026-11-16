package br.com.snackattack.dominio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Jogador {
    private Texture tProta;
    private Sprite prota;

    private float posX = 0, posY = 0;
    private float VELOCIDADE = 5;


    public Jogador() {
        tProta = new Texture("Boneco_NORMAL_A1-removebg-preview.png");
        prota = new Sprite(tProta);
        prota.setSize(1.2f,1.2f);
    }
    public void mover(float delta, float mundoLargura, float mundoAltura){
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
                posX += VELOCIDADE * delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                posY += VELOCIDADE * delta;
            }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                posX -= VELOCIDADE * delta;
        }
        posX = MathUtils.clamp(posX, 0, mundoLargura - prota.getWidth());
        posY = MathUtils.clamp(posY, 0, mundoAltura - prota.getHeight());
        this.prota.setPosition(getPosX(), getPosY());
    }

    public void render(SpriteBatch batch){
        prota.draw(batch);
    }

    public void dispose(){
        tProta.dispose();
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
}
