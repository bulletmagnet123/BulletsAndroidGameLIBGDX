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

    Animation<TextureRegion> IdleAnimation;
    Texture walkSheet;

    float stateTime;

    @Override
    public void show() {
        // Prepare your screen here.
        batch = new SpriteBatch();

        porn = new Texture("com/porn/porn.jpg");
        KnightSheet = new Texture("com/sprites/knight.png");
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pos = new Vector3( Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);

        //COPIED CODE PLEASE REVISE

        // Load the sprite sheet as a Texture


        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(KnightSheet, 32, 32);
        TextureRegion[] KnightIdleFrames = new TextureRegion[FRAME_COLS];
        int index = 0;
        for (int j = 0; j < FRAME_COLS; j++) {
            KnightIdleFrames[index++] = tmp[0][j];
        }
        IdleAnimation = new Animation<TextureRegion>(0.25f, KnightIdleFrames);
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
//        batch.draw(KnightSheet, 50, 50);
        //batch.draw(porn, 0, 0);
        TextureRegion currentFrame = IdleAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 50, 50); // Draw current frame at (50, 50)
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
