package BulletGame.Packag.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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
    public TextButton left, right, up, down;
    public Label controlls;
    Stage stage;


    Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));;
    public TextButton.TextButtonStyle buttonStyle;


    Animation<TextureRegion> IdleAnimation;
    Texture walkSheet;

    float stateTime;

    Player player = new Player("Knight");

    @Override
    public void show() {
        batch = new SpriteBatch();


        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));






        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pos = new Vector3( Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);

        player.getReady(250, 250);


        stateTime = 0f;


        left = new TextButton("Left",skin,"default");
        right = new TextButton("Right",skin,"default");
        up = new TextButton("Up",skin,"default");
        down = new TextButton("Down",skin,"default");
        left.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.moveLeft();
                Gdx.app.log("Clicked", "Left");

            }
        });
        right.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.moveRight();
                Gdx.app.log("Clicked", "Right");

            }
        });
        up.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.moveUp();
                Gdx.app.log("Clicked", "UP");

            }
        });
        down.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.moveDown();
                Gdx.app.log("Clicked", "Down");

            }
        });
        Gdx.input.setInputProcessor(stage);


        controlls = new Label("CONTROLS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(left).width(100).height(100);
        table.add(right).width(100).height(100);
        table.row();
        table.add(up).width(100).height(100);
        table.add(down).width(100).height(100);

        /*stage.addActor(controlls);
        stage.addActor(left);
        stage.addActor(right);
        stage.addActor(up);
        stage.addActor(down);*/
        stage.addActor(table);






    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        camera.update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        player.render(batch);
        player.update(delta);

        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(pos.x, pos.y, 20);
        shapeRenderer.end();

    }


    public void act(float delta) {
        act(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.W)){

        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){

        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){

        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){

        }

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
        skin.dispose();
    }
}
