package com.skayani.brickbreaker.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skayani.brickbreaker.BrickBreaker;

import java.util.Stack;

/** GameStateManager class that stores the different game states in a stack data structure.
 *
 */

public class GameStateManager {
    // Booleans for user settings
    public boolean musicOn = true;
    public float musicVolume = 100;
    public boolean soundOn = true;
    public float soundVolume = 100;

    // General game music and score sound effect declarations.
    public Music playMusic;
    public Music scoreSound;

    // Data structure to hold the different game states
    private Stack<State> states;
    private int size;

    /** Returns the size of the stack (how many total game states are within the stack)
     *
     * @return Int representing the number of game states stored within the states stack
     */
    public int getSize() {
        return size;
    }

    public GameStateManager() {
        playMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/play_state_music.ogg"));
        scoreSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/score_sound.mp3"));
        playMusic.setLooping(true);
        playMusic.play();
        states = new Stack<State>();
        size = 0;
    }

    /** Increases the size member variable by one.
     *
     */
    public void incSize() {
        size += 1;
    }

    /** Decreases the size member variable by one.
     *
     */
    public void decSize() {
        size -= 1;
    }

    /** Pushes a new state onto the stack, making it the current game state.
     *
     * @param state Game state class instance
     */
    public void push(State state) {
        states.push(state);
    }

    /** Removes the current game state from the stack.
     *
     */
    public void pop() {
        states.pop().dispose();
    }

    /** Removes all game states from the stack and pushes a new state, making i the current game
     * state.
     *
     * @param state Game state class instance
     */
    public void set (State state) {
        for (int i = 0; i != size; i++) {
            states.pop().dispose();
        }
        size = 1;
        states.push(state);
    }

    /** Calls the update method for the current game state.
     *
     * @param delta Time difference between two frames
     */
    public void update(float delta) {
        states.peek().update(delta);
    }

    /** Calls the render method for the current game state.
     *
     * @param sb SpriteBatch class instance to render objects
     */
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
