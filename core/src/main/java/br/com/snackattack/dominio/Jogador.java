
package br.com.snackattack.dominio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Jogador extends Entidades{
    private Texture textura;
    private Animation<TextureRegion> animacaoParado;

    private TextureRegion quadroAtual;
    private TextureRegion puloSubindo;
    private TextureRegion puloPico;
    private TextureRegion puloCaindo;

    private float tempoEstado = 0f;


    // configurações da física
    private static final float VELOCIDADE = 6f;
    private static final float FORCA_PULO = 12f;
    private static final float FORCA_PULO_DUPLO = 13f;
    private static final float VELOCIDADE_MAX_QUEDA = -16f;
    private static final float GRAVIDADE_NORMAL = 1.0f;
    private static final float GRAVIDADE_QUEDA = 1.5f;
    private static final float GRAVIDADE_PICO = 0.5f;
    private static final float LIMITE_PULO = 1.5f;
    private static final float CORTE_PULO = 0.45f;
    private static final float TEMPO_COIOTE = 0.1f;
    private static final float BUFFER_PULO = 0.12f;
    private float temporizadorCoiote = 0f;
    private float temporizadorBufferPulo = 0f;
    private int contatosPes = 0;
    private int pulosNoArRestantes = 1;
    private boolean puloPressionado = false;

    // atributos
    private float estomago = 100f;
    private final float ESTOMAGO_MAX = 100f;


    public Jogador(World mundo) {
        // atributos e física
        super(mundo, 2f, 4f, 100f, 10f);

        // animação
        textura = new Texture("spritesheet_boneco.png");
        TextureRegion[] quadros = TextureRegion.split(textura, 113, 113)[0];
        animacaoParado = new Animation<>(0.4f, quadros[3], quadros[4]);
        animacaoParado.setPlayMode(Animation.PlayMode.LOOP);
        puloSubindo = quadros[5];
        puloPico = quadros[6];
        puloCaindo = quadros[7];
        quadroAtual = quadros[3];

        // hitbox
        criarCapsula();
    }

    public void animar(float delta) {
        tempoEstado += delta;
        float velocidadeY = corpo.getLinearVelocity().y;
        boolean noChao = contatosPes > 0 && velocidadeY <= 0.1f;
        if (noChao) {
            quadroAtual = animacaoParado.getKeyFrame(tempoEstado);

        } else if (velocidadeY > 2f) {
            quadroAtual = puloSubindo;

        } else if (velocidadeY < -2f) {
            quadroAtual = puloCaindo;

        } else {
            quadroAtual = puloPico;
        }
    }

    public void lerEntrada() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            temporizadorBufferPulo = BUFFER_PULO;
        }
    }

    public void mover(float delta) {
        Vector2 velocidade = corpo.getLinearVelocity();
        float velocidadeY = velocidade.y;
        float velocidadeX = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocidadeX += VELOCIDADE;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocidadeX -= VELOCIDADE;
        }
        boolean noChao = contatosPes > 0 && velocidadeY <= 0.1f;

        if (noChao) {
            temporizadorCoiote = TEMPO_COIOTE;
            pulosNoArRestantes = 1;
        } else {
            temporizadorCoiote -= delta;
        }

        temporizadorBufferPulo -= delta;
        if (temporizadorBufferPulo > 0f) {
            if (temporizadorCoiote > 0f) {
                velocidadeY = FORCA_PULO;
                temporizadorCoiote = 0f;
                temporizadorBufferPulo = 0f;
            } else if (pulosNoArRestantes > 0) {
                velocidadeY = FORCA_PULO_DUPLO;
                pulosNoArRestantes--;
                temporizadorBufferPulo = 0f;
            }
        }

        boolean espacoPressionado = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if (puloPressionado && !espacoPressionado && velocidadeY > 0f) {
            velocidadeY *= CORTE_PULO;
        }

        puloPressionado = espacoPressionado;
        if (!noChao && Math.abs(velocidadeY) < LIMITE_PULO && espacoPressionado) {
            corpo.setGravityScale(GRAVIDADE_PICO);

        } else if (velocidadeY < 0f) {
            corpo.setGravityScale(GRAVIDADE_QUEDA);

        } else {
            corpo.setGravityScale(GRAVIDADE_NORMAL);
        }

        if (velocidadeY < VELOCIDADE_MAX_QUEDA) {
            velocidadeY = VELOCIDADE_MAX_QUEDA;
        }
        corpo.setLinearVelocity(velocidadeX, velocidadeY);
    }

    public void render(SpriteBatch batch) {
        float largura = 1f;
        float altura = 1f;

        batch.draw(
            quadroAtual,
            corpo.getPosition().x - largura / 2f,
            corpo.getPosition().y - altura / 2f,
            largura,
            altura
        );
    }
    public void dispose() {
        textura.dispose();
    }

    private void criarCapsula() {
        FixtureDef confColisor = new FixtureDef();
        confColisor.density = 1f;
        confColisor.friction = 0f;
        confColisor.restitution = 0f;
        PolygonShape tronco = new PolygonShape();
        tronco.setAsBox(0.25f, 0.25f);
        confColisor.shape = tronco;
        corpo.createFixture(confColisor);
        tronco.dispose();
        CircleShape pe = new CircleShape();
        pe.setRadius(0.25f);
        pe.setPosition(new Vector2(0f, -0.25f));
        confColisor.shape = pe;
        corpo.createFixture(confColisor);
        pe.dispose();
        CircleShape cabeca = new CircleShape();
        cabeca.setRadius(0.25f);
        cabeca.setPosition(new Vector2(0f, 0.25f));
        confColisor.shape = cabeca;
        corpo.createFixture(confColisor);
        cabeca.dispose();
        PolygonShape formaSensor = new PolygonShape();
        formaSensor.setAsBox(0.18f, 0.05f, new Vector2(0f, -0.50f), 0f);
        confColisor.shape = formaSensor;
        confColisor.isSensor = true;
        Fixture sensorPe = corpo.createFixture(confColisor);
        sensorPe.setUserData("pe");
        formaSensor.dispose();
    }

    public float getESTOMAGO_MAX() {
        return ESTOMAGO_MAX;
    }

    public float getEstomago() {
        return estomago;
    }

    public void adicionarContatoPe() {
        contatosPes++;
    }

    public void removerContatoPe() {
        contatosPes = Math.max(0, contatosPes - 1);
    }
}

