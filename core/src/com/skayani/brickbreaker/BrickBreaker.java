package com.skayani.brickbreaker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skayani.brickbreaker.states.GameStateManager;
import com.skayani.brickbreaker.states.MenuState;

/** BrickBreaker class that initializes the GameStateManager and SpriteBatch class instances
 *
 */
public class BrickBreaker extends ApplicationAdapter {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    SpriteBatch batch;
	private GameStateManager gsm;

    /** Method initializes the SpriteBatch (batch) and GameStateManager (gsm) member variables.
     *
     */
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0, 0, 0, 0);
        gsm.incSize();
        gsm.push(new MenuState(gsm));
	}

    /** Method that clears the screen and passes the delta time to the MenuState game state
     * within the gsm stack data structure.
     */
	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

    /** Method that deallocates the SpriteBatch member variable sb.
     *
     */
	@Override
	public void dispose () {
		batch.dispose();
	}
}
