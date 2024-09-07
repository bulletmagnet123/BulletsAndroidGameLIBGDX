package BulletGame.Packag.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMap implements Screen {
    private static final Logger log = LoggerFactory.getLogger(GameMap.class);
    private final World world;
    private TiledMapTileLayer playerCollisionLayer;
    public SpriteBatch batch;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMapHelper tiledMapHelper;
    private Controller controller;
    Player player;
    Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas")));
    public TextButton.TextButtonStyle buttonStyle;
    private Stage stage;


    float stateTime;


    public GameMap(){
        this.camera = new OrthographicCamera();
        this.batch = new SpriteBatch();
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.tiledMapHelper = new TiledMapHelper();

        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap();
        this.world = new World(new Vector2(0, 0), false);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        int tileHeight = tiledMapHelper.getTileHeight();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        controller = new Controller(stage);
        player = new Player("Knight", tiledMapHelper);

        controller.setPosition(75, 75);

        player.position.set(250, 350);
        player.getReady();


    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Step the physics world
        world.step(1 / 60f, 6, 2);

        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();

        batch.begin();
        stage.draw();
        stage.act(delta);

        handleInput();

        player.update(delta);
        player.render(batch);

        camera.update();
        batch.end();
    }



    public void handleInput() {
        if (controller.isRightPressed()) {
            player.moveRight();
        } else if (controller.isLeftPressed()) {
            player.moveLeft();
        } else if (controller.isUpPressed()) {
            player.moveUp();
        } else if (controller.isDownPressed()) {
            player.moveDown();
        }

    }




    private void update(){
        orthogonalTiledMapRenderer.setView(camera);
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
