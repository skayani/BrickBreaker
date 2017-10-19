package com.skayani.brickbreaker.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/** Brick class that represents the breakable bricks on the screen.
 *
 */
public class Brick {
    private Vector2 position;
    private Texture brick;
    private Rectangle bounds;

    /** Brick constructor class that takes in an X-coordinate, Y-coordinate, and a brickColor.
     *
     * @param x X-coordinate of the brick's initial position
     * @param y Y-coordinate of the brick's initial position
     * @param brickColor Brick's color
     */
    public Brick(int x, int y, String brickColor){
        position = new Vector2(x, y);
        String brickPath = "bricks/" + brickColor;
        brick = new Texture(brickPath);
        bounds = new Rectangle(x, y, brick.getWidth(), brick.getHeight());
    }

    /** Method called when PlayState is removed from the game states stack to then deallocate
     * the brick resources.
     */
    public void dispose() {
        brick.dispose();
    }

    /** Returns the brick texture width.
     *
     * @return Float representing the brick texture width
     */
    float getTextureWidth() {
        return brick.getWidth();
    }

    /** Returns the brick texture height.
     *
     * @return Float representing the brick texture height
     */
    float getTextureHeight() { return brick.getHeight();}

    /** Returns a Vector2 of the brick position.
     *
     * @return Vector2 representing the brick's position (x-coordinate, y-coordinate)
     */
    public Vector2 getPosition() {
        return position;
    }

    /** Returns Texture class instance of the brick
     *
     * @return Texture class instance of the brick image resource
     */
    public Texture getTexture() {
        return brick;
    }

    /** Returns a Rectangle class instance representing the brick boundaries.
     *
     * @return Rectangle representing the brick bounds
     */
    Rectangle getBounds() {
        return bounds;
    }
}
