package BulletGame.Packag.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    SpriteBatch batch;
    Texture porn;
    Texture KnightSheet;
    private OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    Vector3 pos;
    private static final int FRAME_COLS = 4, FRAME_ROWS = 1;
    float scaleFactor = 2f;

    Animation<TextureRegion> IdleAnimation;
    Texture walkSheet;

    float stateTime;

    Core.Main.Player player = new Core.Main.Player("Knight");

    @Override
    public void show() {
        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pos = new Vector3( Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);

        player.getReady(250, 250);


        stateTime = 0f;




    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        camera.update();
        player.update(delta);
        player.render(batch);


        if (Gdx.input.isTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();
            System.out.println("x: " + x + " y: " + y);
            pos.set(x, y, 0);
        }
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(pos.x, pos.y, 20);
        shapeRenderer.end();


    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
