package br.com.snackattack.dominio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Jogador {
    private Texture tProta;
    private Sprite prota;

    private Body corpo;
    private PolygonShape caixa;
    private FixtureDef confColisor;
    private Fixture colisor;

    private float posX = 0, posY = 0;
    private float VELOCIDADE = 5;

    public Jogador(World world) {
        tProta = new Texture("Boneco_NORMAL_A1-removebg-preview.png");
        prota = new Sprite(tProta);
        prota.setSize(1.2f,1.2f);

        BodyDef confCorporal = new BodyDef();
        confCorporal.type = BodyDef.BodyType.DynamicBody;
        confCorporal.position.set(4,2); // posição incial do body
        confCorporal.fixedRotation = true;
        corpo = world.createBody(confCorporal);
        caixa = new PolygonShape();  // HitBox do personagem
        caixa.setAsBox(0.6f,0.6f);  // Define metade da largura e da altura da HitBox

        confColisor = new FixtureDef();
        confColisor.shape = caixa;
        confColisor.density = 1f;  // densidade entra na parte da física
        confColisor.friction = 0.2f; // atrito para não sair deslizando
        confColisor.restitution = 0.0f; // não quica
        colisor = corpo.createFixture(confColisor);
        caixa.dispose(); // Libera a Shape após criar a Fixture
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

    public void update(){
        prota.setPosition(
            corpo.getPosition().x - prota.getWidth() / 2,
            corpo.getPosition().y - prota.getHeight() / 2
        ); // conectando o Sprite prota a HitBox para a física funcionar
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
