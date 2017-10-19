package com.skayani.brickbreaker.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.skayani.brickbreaker.BrickBreaker;
import com.skayani.brickbreaker.sprites.Ball;
import com.skayani.brickbreaker.sprites.Brick;
import com.skayani.brickbreaker.sprites.BrickManager;
import com.skayani.brickbreaker.sprites.PlayerBrick;

import java.util.Hashtable;

/** PlayState class that is instantiated when user presses a level from LevelSelectState
 *
 */
public class PlayState extends State {
    private BitmapFont bitmapFont;
    private int playerScore;
    private String scoreString;
    private String levelString;
    private Texture pauseButton;
    private Texture background;
    private Rectangle pauseButtonBounds;
    private PlayerBrick playerBrick;
    private Ball ball;
    private BrickManager brickManager;
    private boolean acelleratorAvail;
    private int level;

    /** Over-ridden PlayState constructor to take in a GameStateManager instance as well as an
     * int representing the level the user chose from the LevelSelect State.
     *
     * @param gsm GameStateManager class instance
     * @param level Integer representing the level the user select from the LevelSelectState
     */
    PlayState(GameStateManager gsm, int level) {
        super(gsm);
        playerBrick = new PlayerBrick((int)(cam.position.x), 0);
        ball = new Ball((int)cam.position.x, 60);
        brickManager = new BrickManager(gsm, ball, level);
        bitmapFont = new BitmapFont();
        scoreString = "Score: 0";
        levelString = "Level " + level;
        playerScore = brickManager.getPlayerScore();
        pauseButton = new Texture("pause_button.png");
        background = new Texture("backgrounds/play_state_background.jpg");
        pauseButtonBounds = new Rectangle (cam.viewportWidth-60,
                BrickBreaker.HEIGHT-cam.viewportHeight,
                pauseButton.getWidth(), pauseButton.getHeight());

        // Boolean to check if user is playing on a device with a supported accelerometer
        acelleratorAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        this.level = level;
    }

    /** Method to record user movement input.  Accelerometer readings are recorded if
     * acceleratorAvail == true.  Otherwise, keyboard recordings (left key and right key) are
     * recorded.
     */
    @Override
    public void handleInput() {
        if (acelleratorAvail) {
            float accelX = Gdx.input.getAccelerometerX();
            playerBrick.move(accelX);
        }
        else {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                playerBrick.move(1);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                playerBrick.move(-1);
            }
        }
    }

    /** Method called to constantly check user input as well as game state changes such as if
     * the user has crushed all the bricks or if the user has crushed a particular brick on the
     * screen.
     *
     * @param delta Time difference between two frames
     */
    @Override
    public void update(float delta) {
        handleInput();
        if (checkGameOver()) {
            gsm.set(new GameOverState(gsm, true, playerScore, level));
        }
        if (Gdx.input.justTouched() && pauseButtonBounds.contains(Gdx.input.getX(),
                Gdx.input.getY())) {
            gsm.incSize();
            gsm.push(new PreferencesState(gsm));
        }
        playerBrick.update(delta);
        ball.update(delta);
        playerScore = brickManager.getPlayerScore();
        scoreString = "Score: " + playerScore;
        if (ball.collidesVertical()) {
            ball.flipVelocityX();
        }
        else if (ball.collidesHorizontal() || ball.collidesPlayerBrick(playerBrick.getBounds())) {
            ball.flipVelocityY();
        }
        else if (ball.ballOut()) {
            gsm.set(new GameOverState(gsm, false, playerScore, level));
        }
        brickManager.updateBricks();
    }

    /** Method called to render background, pause button, player score, and brick objects onto
     * the screen.
     *
     * @param sb SpriteBatch class instance to render objects
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0, BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
        sb.draw(pauseButton, cam.viewportWidth-60, cam.viewportHeight-60);
        bitmapFont.draw(sb, scoreString, 15, cam.viewportHeight-15);
        bitmapFont.draw(sb, levelString, cam.viewportWidth/2-15, cam.viewportHeight-15);
        sb.draw(playerBrick.getTexture(), playerBrick.getPosition().x,
                playerBrick.getPosition().y);

        for (Rectangle key : brickManager.bricksTable.keySet()) {
            Brick brick = brickManager.bricksTable.get(key);
            sb.draw(brick.getTexture(), brick.getPosition().x, brick.getPosition().y);

        }

        sb.draw(ball.getTexture(), ball.getPosition().x, ball.getPosition().y);
        sb.end();
    }

    /** Method called when switched from PlayState to another game state to free memory
     * allocation.
     */
    @Override
    public void dispose() {
        pauseButton.dispose();
        background.dispose();
        playerBrick.dispose();
        ball.dispose();
        brickManager.dispose();
    }

    /** Method called from update method to check if game over conditions have been fulfilled
     * (i.e. no more bricks left)
     */
    private boolean checkGameOver() {
        return brickManager.getRemainingBricks() == 0;
    }
}
