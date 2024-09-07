package BulletGame.Packag.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.aliasifkhan.hackLights.HackLight;
import com.aliasifkhan.hackLights.HackLightEngine;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    SpriteBatch batch;
    private OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    Vector3 pos;
    Stage stage;
    Box2DDebugRenderer debugRenderer;
    private HackLight fogLight, libgdxLight;
    private HackLightEngine lightEngine;
    public Viewport gameViewport;
    private Controller controller;
    public static final float PPM = 100;
    Viewport viewport;
    GameMap map;
    TiledMap MapGame = new TiledMap();
    private TiledMapTileLayer playerCollisionLayer;



    Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));
    public TextButton.TextButtonStyle buttonStyle;

    float stateTime;

    Player player = new Player("Knight", new TiledMapHelper());

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        map = new GameMap();


        MapGame = new TiledMap();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(Gdx.graphics.getWidth()/ PPM, Gdx.graphics.getHeight() / PPM, camera);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 450);
        pos = new Vector3(viewport.getScreenWidth(), viewport.getScreenHeight(), 0);



        controller = new Controller(stage);



        stateTime = 0f;



        TextureAtlas lightsAtlas = new TextureAtlas(Gdx.files.internal("lights.atlas"));
        TextureRegion libgdxLight = lightsAtlas.findRegion("light2");

        lightEngine = new HackLightEngine();
        lightEngine.addLight(this.libgdxLight = new HackLight(libgdxLight, 1, 1, 1, 1, 5f));





    }
    /*public void handleInput(){
        if (controller.isRightPressed())
            player.setMovement(player.MOVEMENT_SPEED, 0); // Move right
        else if (controller.isLeftPressed())
            player.setMovement(-player.MOVEMENT_SPEED, 0); // Move left
        else if (controller.isDownPressed())
            player.setMovement(0, -player.MOVEMENT_SPEED); // Move down
        else if (controller.isUpPressed())
            player.setMovement(0, player.MOVEMENT_SPEED); // Move up
        else {
            player.stop();
        }
    }*/

    /*public void update(float dt){
        handleInput();
    }*/

    @Override
    public void render(float delta) {
        //Vector2 vec = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        //gameViewport.unproject(vec);
        //update(delta);
        batch.begin();
        World world = new World(new Vector2(0, 0), false);
        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();


        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
        camera.setToOrtho(false, 800, 450);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        camera.viewportWidth = 800;
        camera.viewportHeight = 450;
        world.step(1/60f, 6, 2);
        BodyHelperService.createbody(250, 250, 100, 100, false, world);
        debugRenderer.render(world, camera.combined);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        controller.setPosition(100, 100);
        controller.draw();




        camera.position.set(pos.x, pos.y, 0);
        //lighting code



        //player.render(batch);
        //player.update(delta);


        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        /*shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(pos.x, pos.y, 20);*/
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
