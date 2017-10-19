package com.skayani.brickbreaker.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skayani.brickbreaker.BrickBreaker;

/** Abstract State class for the various game states: PlayState, MenuState, etc.
 *
 */

public abstract class State {
    protected GameStateManager gsm;
    public OrthographicCamera cam;

    /** Default constructor for State class
     *
     * @param gsm GameStateManager class instance that is passed around to each state so as to
     *            avoid wasting memory allocation
     */
    public State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
    }

    /** Method to handle user input
     *
     */
    public abstract void handleInput();

    /** Method that is constantly called to update the screen
     *
     * @param delta Time difference between two frames
     */
    public abstract void update(float delta);

    /** Method to render objects onto the screen
     *
     * @param sb SpriteBatch class instance to render objects
     */
    public abstract void render(SpriteBatch sb);

    /** Method called when there is a switch between game states, to dispose of any Textures (to
     * free up memory allocation)
     */
    public abstract void dispose();
}
