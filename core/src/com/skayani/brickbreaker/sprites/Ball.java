package com.skayani.brickbreaker.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.skayani.brickbreaker.BrickBreaker;

import java.util.Random;

/** Ball class that represents the moving ball on the screen.
 *
 */

public class Ball {
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Texture ball;
    private Random randNum;


    /** Returns a Vector2 representing the ball position.
     *
     * @return Vector2 representing the ball position
     */
    public Vector2 getPosition() {
        return position;
    }

    /** Returns a Vector2 representing the vall velocity.
     *
     * @return Vector2 representing the ball velocity.
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /** Returns the Texture class instance of the ball image.
     *
     * @return Texture clsas instance of the ball
     */
    public Texture getTexture() {
        return ball;
    }

    /** Returns a rectangle representing the boundaries of the ball.
     *
     * @return Rectangle representing the ball boundaries.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /** Ball constructor class that takes in an x-coordinate and y-coordinate representing the
     * ball's initial position on the screen.
     *
     * @param x X-coordinate of the ball position
     * @param y Y-coordinate of the ball position
     */
    public Ball (int x, int y) {
        ball = new Texture("redball.png");
        randNum = new Random();
        position = new Vector2(x, y);
        velocity = new Vector2(4, -4);
        bounds = new Rectangle(x, y, ball.getWidth(), ball.getHeight());
    }

    /** Method that is called when the ball position changes.
     *
     * @param delta Time difference between two frames
     */
    public void update(float delta) {
        position.add(velocity);
        bounds.setPosition(position.x, position.y);
    }

    /** Checks whether the ball has collided with either the left wall or the right wall.
     *
     * @return Boolean that is true if the ball has collided with left or right wall.
     */
    public boolean collidesVertical() {
        // Check if X bound hits either left or right screen border
        return (bounds.x < 0) || (bounds.x + ball.getWidth()) >= BrickBreaker.WIDTH;
    }

    /** Checks whether the ball has collided with either the top wall or if the ball has gone
     * past the movable player brick.
     *
     * @return Boolean that is true if the ball has collided with the top wall or if the ball
     * has gone past the movable player brick.
     */
    public boolean collidesHorizontal() {
        // Check if Y bound hits either top border
        return (bounds.y + ball.getHeight() >= BrickBreaker.HEIGHT);
    }

    /** Checks whether the ball has collided with the movable player brick.
     *
     * @param playerBrick Rectangle representing the ball bounds.
     * @return Boolean that is true if the ball has collided with player brick
     */
    public boolean collidesPlayerBrick(Rectangle playerBrick) {
        // Check if ball collides with moving brick
        return playerBrick.overlaps(bounds);
    }

    /** Checks whether the ball has gone out of bounds.
     *
     * @return Boolean that is true if the ball is out of bounds
     */
    public boolean ballOut() {
        // Check if ball is out of bounds
        return position.y < -5;
    }

    /** Flips the X direction of the ball movement.
     *
     */
    public void flipVelocityX() {
        velocity.x *= -1;
    }

    /** Flips the Y direction of the ball movement.
     *
     */
    public void flipVelocityY() {
        velocity.y *= -1;
    }

    /** Adds velocity to the ball given a Vector2.
     *
      * @param addedVelocity Vector2 representing the velocity (x, y) to be added to the ball.
     */
    public void addVelocity(Vector2 addedVelocity) {
        velocity.add(addedVelocity.x, addedVelocity.y);
    }

    /** Method called when the PlayState is removed from the GameStateManger stack to
     * dispose/deallocate the ball image resource.
     */
    public void dispose() {
        ball.dispose();
    }
}
