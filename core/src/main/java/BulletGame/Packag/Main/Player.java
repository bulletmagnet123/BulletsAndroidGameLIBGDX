
package BulletGame.Packag.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static BulletGame.Packag.Main.Player.State.ATTACK;
import static BulletGame.Packag.Main.Player.State.IDLE;
import static BulletGame.Packag.Main.Player.State.RUN;

import javax.swing.JComboBox;

public class Player {
    Texture KnightSheet;
    private static final int FRAME_COLS = 4, FRAME_ROWS = 1;
    private String name;
    private Color color;
    public enum State {
        IDLE,
        RUN,
        ATTACK,
        DEATH
    }




    public State state;
    public float MOVEMENT_SPEED = 25.0f;
    public float stateTime;
    public State renderState = IDLE;
    public float renderStateTime;
    public final Vector2 position = new Vector2();
    public final Vector2 movementDirection = new Vector2();

    private Animation<TextureRegion> IdleAnimation;
    private Animation<TextureRegion> Attackanimation;
    private Animation<TextureRegion> Runanimation;
    private Animation<TextureRegion> DeathAnimation;


    public Player(String name) {
        this.name = name;
        changeState(IDLE);
        KnightSheet = new Texture("com/sprites/knight.png");
    }

    public void getAnimation(){

    }

    public TextureRegion State(){
        TextureRegion currentFrame;
        renderState = state;

        switch (renderState) {
            case IDLE:
                currentFrame = IdleAnimation.getKeyFrame(stateTime, true);

                break;
            case RUN:
                currentFrame = Runanimation.getKeyFrame(stateTime, true);

                break;
            case ATTACK:
                currentFrame = Attackanimation.getKeyFrame(stateTime, false);

                break;
            case DEATH:
                currentFrame = DeathAnimation.getKeyFrame(stateTime, false);

                break;
            default:
                currentFrame = IdleAnimation.getKeyFrame(stateTime, true);

        }
        return currentFrame;
    }





    public void render(SpriteBatch batch) {
        System.out.println("Player Position: " + position.x + ", " + position.y);
        TextureRegion currentFrame = IdleAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame,Player.this.position.x, Player.this.position.y , 300, 300);



    }

    public void update(float deltaTime) {

            stateTime += deltaTime;

            if (deltaTime > 0){
                renderState = state;
                renderStateTime = stateTime;
            }
            position.x += movementDirection.x * deltaTime;
            position.y += movementDirection.y * deltaTime;

            System.out.println("Player Position: " + position.x + ", " + position.y);
    }
    public float getPositionX() {
        return position.x;
    }
    public float getPositionY() {
        return position.y;
    }
    private void setMovement(float x, float y){
        movementDirection.set(x, y);
        if (state == RUN && x == 0 && y == 0){
            changeState(IDLE);
        } else if (state == IDLE && (x != 0 || y != 0)){
            changeState(RUN);
        }
    }
    public void getReady(float positionX, float positionY){
        //state = renderState = IDLE;
        stateTime = renderStateTime = 0f;
        position.set(positionX, positionY);
        movementDirection.set(0, 0);
        TextureRegion[][] tmp = TextureRegion.split(KnightSheet, 32, 32);
        TextureRegion[] KnightIdleFrames = new TextureRegion[FRAME_COLS];
        int index = 0;
        for (int j = 0; j < FRAME_COLS; j++) {
            KnightIdleFrames[index++] = tmp[0][j];
        }
        IdleAnimation = new Animation<TextureRegion>(0.25f, KnightIdleFrames);
        stateTime = 0f;


    }


    private void changeState(State newState){
        state = newState;
        stateTime = 0f;
    }
    public State returnState(){
        return state;
    }

    public void Attack(){
        changeState(ATTACK);
    }
    public void StopAttack(){
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
        position.y += MOVEMENT_SPEED;
        changeState(RUN);
        System.out.println("Position after: " + position);
    }

    public void moveDown() {
        System.out.println("Position before: " + position);
        position.y -= MOVEMENT_SPEED;
        changeState(RUN);
        System.out.println("Position after: " + position);
    }

    public void stopMovingX() {
        movementDirection.x = 0;
        changeState(IDLE);

    }

    public void stopMovingY() {
        movementDirection.y = 0;
        changeState(IDLE);

    }

    public void stopMovingLeft(){
        if (movementDirection.x == -1){
            setMovement(0, movementDirection.y);
            changeState(IDLE);
        }
    }
    public void stopMovingRight(){
        if (movementDirection.x == 1){
            setMovement(0, movementDirection.y);
            changeState(IDLE);
        }
    }
    public void stopMovingUp(){
        if (movementDirection.y == 1){
            setMovement(movementDirection.x, 0);
            changeState(IDLE);
        }
    }
    public void stopMovingDown(){
        if (movementDirection.y == -1){
            setMovement(movementDirection.x, 0);
            changeState(IDLE);
        }
    }
}
