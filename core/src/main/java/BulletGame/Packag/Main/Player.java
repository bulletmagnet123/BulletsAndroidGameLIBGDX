package BulletGame.Packag.Main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static BulletGame.Packag.Main.Player.State.IDLE;
import static BulletGame.Packag.Main.Player.State.RUN;


public class Player {
    private Texture KnightSheet;
    private TiledMapHelper tiledMapHelper;

    private Vector2 velocity = new Vector2();
    private final Vector2 position = new Vector2();
    public float MOVEMENT_SPEED = 5.0f;
    private float stateTime = 0f;

    private Animation<TextureRegion> IdleAnimation, Runanimation, Attackanimation, DeathAnimation;
    private State state = State.IDLE;

    private static final int FRAME_COLS_IDLE = 4;


    public Rectangle player_rect;
    private TiledMap tiledMap;

    public enum State {
        IDLE, RUN, ATTACK, DEATH
    }

    public Player(String name, TiledMapHelper tiledMapHelper) {
        this.tiledMapHelper = tiledMapHelper; // Correctly assign TiledMapHelper
        KnightSheet = new Texture("com/sprites/knight.png");
        setupAnimations();
        state = State.IDLE;
        player_rect = new Rectangle();
        player_rect.set(getPositionX(), getPositionY(), 32, 32);
        this.tiledMap = tiledMapHelper.getTiledMap();

    }

    private void setupAnimations() {

        TextureRegion[][] tmp = TextureRegion.split(KnightSheet, 32, 32);
        TextureRegion[] KnightIdleFrames = new TextureRegion[FRAME_COLS_IDLE];
        int indexIDLE = 0;
        for (int j = 0; j < FRAME_COLS_IDLE; j++) {
            KnightIdleFrames[indexIDLE++] = tmp[0][j];
        }
        IdleAnimation = new Animation<TextureRegion>(0.25f, KnightIdleFrames);
        stateTime = 0f;

        TextureRegion[] KnightRunFrames = new TextureRegion[16];  // 16 frames total (8 from each
        int indexRUN = 0;
        for (int j = 0; j < 8; j++) { // 4 frames from the third row
            KnightRunFrames[indexRUN++] = tmp[2][j]; // Row index 2 for the third row
        }
        for (int j = 0; j < 8; j++) { // 4 frames from the fourth row
            KnightRunFrames[indexRUN++] = tmp[3][j]; // Row index 3 for the fourth row
        }
        Runanimation = new Animation<TextureRegion>(0.1f, KnightRunFrames);
    }

    public void getReady(float positionX, float positionY) {
        position.set(positionX, positionY);

        changeState(IDLE);

        stateTime = 0f;
    }


    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();
        player_rect.set(position.x, position.y, 64, 64); // Update player rectangle
        batch.draw(currentFrame, position.x, position.y, player_rect.width, player_rect.height);

    }


    public void update(float deltaTime) {
        stateTime += deltaTime;

        float oldX = position.x;
        float oldY = position.y;
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;

        if (checkCollision()){

        }

        player_rect.setPosition(position.x, position.y);

    }

    public TextureRegion getCurrentFrame() {
        switch (state) {
            case IDLE:
                return IdleAnimation.getKeyFrame(stateTime, true);
            case RUN:
                return Runanimation.getKeyFrame(stateTime, true);
            case ATTACK:
                return Attackanimation.getKeyFrame(stateTime, false);
            case DEATH:
                return DeathAnimation.getKeyFrame(stateTime, false);
            default:
                return IdleAnimation.getKeyFrame(stateTime, true);
        }
    }


    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }


    private void changeState(State newState) {
        if (state != newState) { // Only reset stateTime if the state changes
            state = newState;
            stateTime = 0f;
        }
    }



    public void stop() {
        velocity.set(0, 0);

        changeState(IDLE);
    }

    public void moveLeft() {
        System.out.println("Position before: " + position);
        position.x -= MOVEMENT_SPEED;
        changeState(RUN);
        System.out.println("Position after: " + position);
    }

    public void moveRight() {
        System.out.println("Position before: " + position);
        position.x += MOVEMENT_SPEED;
        changeState(RUN);
        System.out.println("Position after: " + position);
    }

    public void moveUp() {
        System.out.println("Position before: " + position);
        changeState(RUN);
        position.y += MOVEMENT_SPEED;

        System.out.println("Position after: " + position);
    }

    public void moveDown() {
        System.out.println("Position before: " + position);
        position.y -= MOVEMENT_SPEED;
        changeState(RUN);
        System.out.println("Position after: " + position);
    }


    public boolean checkCollision() {
        if (tiledMap == null) return false;

        int tileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
        int tileHeight = tiledMap.getProperties().get("tileheight", Integer.class);

        // Check collisions in 4 corners of player rectangle
        boolean collision = checkCollisionAt(position.x, position.y, tileWidth, tileHeight) ||
            checkCollisionAt(position.x + player_rect.width, position.y, tileWidth, tileHeight) ||
            checkCollisionAt(position.x, position.y + player_rect.height, tileWidth, tileHeight) ||
            checkCollisionAt(position.x + player_rect.width, position.y + player_rect.height, tileWidth, tileHeight);

        if (collision) {
            System.out.println("Collision detected!");
        }

        return collision;
    }

    private boolean checkCollisionAt(float x, float y, int tileWidth, int tileHeight) {
        int tileX = (int) (x / tileWidth);
        int tileY = (int) (y / tileHeight);

        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("layer1"); // Replace "yourCollisionLayer" with the actual name
        if (collisionLayer == null) return false;

        TiledMapTileLayer.Cell tile = collisionLayer.getCell(tileX, tileY);
        if (tile != null && tile.getTile() != null) {
            if (tile.getTile().getProperties().containsKey("blocked")) {
                System.out.println("Collision at: " + x + ", " + y);
                return true;
            }
        }
        return false;
    }

}


