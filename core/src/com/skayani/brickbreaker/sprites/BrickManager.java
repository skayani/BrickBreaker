package com.skayani.brickbreaker.sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.skayani.brickbreaker.BrickBreaker;
import com.skayani.brickbreaker.states.GameStateManager;

import java.util.Hashtable;
import java.util.Random;

/** BrickManager class that handles each individual bricks status such as whether a brick has been
 * hit or not.
 */

public class BrickManager {
    private int bricksCount;
    private int remainingBricks;
    private Array<String> brickColors;
    private Ball ball;
    private int playerScore;
    private Random colorGenerator;
    private GameStateManager gsm;
    public Hashtable<Rectangle, Brick> bricksTable;

    /** Returns the player score (kept track of by the BrickManager
     *
     * @return Integer representing the player's score
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /** Returns the remaning brick count
     *
     * @return Integer representing the remaining bricks on the screen
     */
    public int getRemainingBricks() {
        return remainingBricks;
    }

    /** BrickManager constructor that takes in a GameStateManager class instance, a Ball class
     * instance, and a integer representing the game level.
     *
     * @param gsm GameStateManager class instance
     * @param ballParam Ball class instance representing the ball
     * @param level Integer representing the game level
     */
    public BrickManager(GameStateManager gsm, Ball ballParam, int level) {
        bricksTable = new Hashtable<Rectangle, Brick>();
        brickColors = new Array<String>() {{
            add("BlueBrick.png");
            add("GreenBrick.png");
            add("OrangeBrick.png");
            add("PinkBrick.png");
            add("PurpleBrick.png");
            add("TealBrick.png");
            add("YellowBrick.png");
        }};
        ball = ballParam;
        colorGenerator = new Random();
        playerScore = 0;
        this.gsm = gsm;

        switch (level) {
            case 1:
                bricksCount = remainingBricks = 16;
                initializeLevelOne();
                break;

            case 2:
                bricksCount = remainingBricks = 18;
                initializeLevelTwo();
                break;

            case 3:
                bricksCount = remainingBricks = 28;
                initializeLevelThree();
                break;
        }
    }

    /** Initializes game level one by initializing the bricks and inserting them into the
     * bricksTable data structure.
     */
    private void initializeLevelOne() {
        int initialX = 50;
        int initialY = 600;
        for (int i = 1; i <= bricksCount; i++) {
            Brick tempBrick = new Brick(initialX, initialY,
                    brickColors.get(colorGenerator.nextInt(brickColors.size)));
            if (i != 1 && i != 4 && i != 13 && i != 16) {
                bricksTable.put(tempBrick.getBounds(), tempBrick);
            }
            if (i == 2) {
                int topBrickX = initialX + (((int)tempBrick.getTextureWidth() + 40) / 2);
                int topBrickY = initialY + 100;
                tempBrick = new Brick(topBrickX, topBrickY,
                        brickColors.get(colorGenerator.nextInt(brickColors.size)));
                bricksTable.put(tempBrick.getBounds(), tempBrick);
                int lowBrickY = initialY - 400;
                tempBrick = new Brick(topBrickX, lowBrickY,
                        brickColors.get(colorGenerator.nextInt(brickColors.size)));
                bricksTable.put(tempBrick.getBounds(), tempBrick);
            }
            if (i == 9) {
                int lowBrickLeftY = initialY - 200;
                tempBrick = new Brick(initialX, lowBrickLeftY,
                        brickColors.get(colorGenerator.nextInt(brickColors.size)));
                bricksTable.put(tempBrick.getBounds(), tempBrick);
                int lowBrickRightX = initialX + 3 * ((int)tempBrick.getTextureWidth() + 40);
                tempBrick = new Brick(lowBrickRightX, lowBrickLeftY,
                        brickColors.get(colorGenerator.nextInt(brickColors.size)));
                bricksTable.put(tempBrick.getBounds(), tempBrick);
            }
            initialX += tempBrick.getTextureWidth() + 40;
            if (i % 4  == 0) {
                initialY -= 100;
                initialX = 50;
            }
        }
    }

    /** Initializes game level two by initializing the bricks and inserting them into the
     * bricksTable data structure.
     */
    private void initializeLevelTwo() {
        int initialX = 50;
        int initialY = 700;
        for (int i = 1; i <= 4; ++i) {
            Brick tempBrick = new Brick(initialX, initialY,
                    brickColors.get(colorGenerator.nextInt(brickColors.size)));
            bricksTable.put(tempBrick.getBounds(), tempBrick);
            initialX = (int)(BrickBreaker.WIDTH - (tempBrick.getTextureWidth() + 50));
            if (i % 2 == 0) {
                initialX = 50;
                initialY = 400;
            }
        }
        initialX = 150;
        initialY = 625;
        for (int i = 1; i <= 6; ++i) {
            Brick tempBrick = new Brick(initialX, initialY,
                    brickColors.get(colorGenerator.nextInt(brickColors.size)));
            bricksTable.put(tempBrick.getBounds(), tempBrick);
            initialX += tempBrick.getTextureWidth() + 50;
            if (i % 2 == 0) {
                initialX = 150;
                initialY -= 75;
            }
        }
        initialX = 50;
        initialY = 325;
        for (int i = 1; i <= 8; ++i) {
            Brick tempBrick = new Brick(initialX, initialY,
                    brickColors.get(colorGenerator.nextInt(brickColors.size)));
            bricksTable.put(tempBrick.getBounds(), tempBrick);
            initialX += tempBrick.getTextureWidth() + 41;
            if (i % 4 == 0) {
                initialX = 50;
                initialY -= 75;
            }
        }
    }

    /** Initializes game level three by initializing the bricks and inserting them into the
     * bricksTable data structure.
     */
    private void initializeLevelThree() {
        int initialX1 = 20;
        int initialX2 = 20;
        int initialY1 = 700;
        // 32 is the brick texture height (adding 20 to it for extra space)
        int initialY2 = initialY1 - 52;
        for (int i = 1; i <= 12; ++i) {
            Brick tempBrick = new Brick(initialX1, initialY1,
                    brickColors.get(colorGenerator.nextInt(brickColors.size)));
            bricksTable.put(tempBrick.getBounds(), tempBrick);
            tempBrick = new Brick(initialX2, initialY2,
                    brickColors.get(colorGenerator.nextInt(brickColors.size)));
            bricksTable.put(tempBrick.getBounds(), tempBrick);
            if (i == 3) {
                int middleX = (initialX2 + 2 * ((int)tempBrick.getTextureWidth() + 10));
                tempBrick = new Brick(middleX, initialY2,
                        brickColors.get(colorGenerator.nextInt(brickColors.size)));
                bricksTable.put(tempBrick.getBounds(), tempBrick);
                tempBrick = new Brick(middleX + (int)tempBrick.getTextureWidth() + 10,
                        initialY2, brickColors.get(colorGenerator.nextInt(brickColors.size)));
                bricksTable.put(tempBrick.getBounds(), tempBrick);
                tempBrick = new Brick(middleX,
                        initialY2-52, brickColors.get(colorGenerator.nextInt(brickColors.size)));
                bricksTable.put(tempBrick.getBounds(), tempBrick);
                tempBrick = new Brick(middleX + (int)tempBrick.getTextureWidth() + 10,
                        initialY2-52, brickColors.get(colorGenerator.nextInt(brickColors.size)));
                bricksTable.put(tempBrick.getBounds(), tempBrick);
            }
            initialY2 -= tempBrick.getTextureHeight() + 20;
            initialX1 += tempBrick.getTextureWidth() + 10;
            if (i == 6) {
                initialX2 = (initialX1 - (int)tempBrick.getTextureWidth()) - 10;
                initialY2 = initialY1 - 52;
                initialX1 = 20;
                initialY1 = 335;
            }
        }
    }

    /** Called from the update method in the PlayState class to update each brick status.
     *
      */
    public void updateBricks() {
        for (Rectangle key : bricksTable.keySet()) {
            Brick brick = bricksTable.get(key);
            if (ball.getBounds().overlaps(brick.getBounds())) {
                remainingBricks -= 1;
                playerScore += 20;
                ball.flipVelocityY();
                if (gsm.soundOn) {
                    gsm.scoreSound.play();
                }
                bricksTable.remove(key);
                break;
            }
        }
    }

    /** Called from the dispose method in the PlayState to dispose of each brick resource in the
     * bricksTable data structure.
     *
     */
    public void dispose() {
        for (Rectangle key : bricksTable.keySet()) {
            bricksTable.get(key).dispose();
        }
    }
}
