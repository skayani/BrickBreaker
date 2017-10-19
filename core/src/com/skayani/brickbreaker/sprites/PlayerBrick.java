package com.skayani.brickbreaker.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.skayani.brickbreaker.BrickBreaker;

/** PlayerBrick class that represents the movable player brick
 *
 */

public class PlayerBrick {
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Texture playerBrick;

    /** Returns a Vector2 representing the player brick velocity.
     *
     * @return Vector2 representing the player brick velocity (X-coordinate, Y-cordinate)
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /** PlayerBrick constructor that takes in an X-coordinate and Y-coordinate representing
     * the player brick's initial position.
     *
     * @param x X-coordinate of the player brick position
     * @param y Y-coordinate of the player brick position
     */
    public PlayerBrick(int x, int y) {
        playerBrick = new Texture("playerbrick.png");
        position = new Vector2(x - (playerBrick.getWidth() / 2), y);
        bounds = new Rectangle(x, y, playerBrick.getWidth(), playerBrick.getHeight());
        velocity = new Vector2(0, 0);
    }

    /** Method that is called from PlayerState to update the player brick position as the user
     * inputs control.
     *
     * @param delta Time difference between two frames
     */
    public void update(float delta) {
        velocity.scl(4);
        position.add(velocity.x, 0);
        bounds.setPosition(position.x, position.y);
        if ((bounds.x + bounds.getWidth()) > BrickBreaker.WIDTH) {
            position.x = BrickBreaker.WIDTH - bounds.getWidth();
        }
        else if (bounds.x <= 0) {
            position.x = 0;
        }
        velocity.set(0, 0);
    }

    /** Moves the player brick along the X-axis (direction depending on the user input).
     *
     * @param x Float representing the velocity/direction to add to the player brick velocity
     *          vector
     */
    public void move(float x) {
        velocity.add(-x, 0);
    }

    /** Method called when the PlayState is removed from the GameStateManager stack to deallocate
     * any resources/assets.
     */
    public void dispose() {
        playerBrick.dispose();
    }

    /** Returns a Vector2 representing the player brick position.
     *
     * @return Vector2 representing the player brick's current position
     */
    public Vector2 getPosition() {
        return position;
    }

    /** Returns a Texture class instance representing the player brick.
     *
     * @return Texture class instance representing the player brick
     */
    public Texture getTexture() {
        return playerBrick;
    }

    /** Returns a Rectangle representing the boundaries of the player brick
     *
     * @return Rectangle representing the bounds of the player brick
     */
    public Rectangle getBounds() {
        return bounds;
    }
}