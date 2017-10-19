package com.skayani.brickbreaker.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skayani.brickbreaker.BrickBreaker;

/** GameOverState that is instantiated when the ball is out of bounds (lose) or if the user
 * breaks all bricks in the game (win).
 */

public class GameOverState extends State {
    private Stage stage;
    private Skin skin;
    private Table table;

    private Label gameLabel;
    private Label lostLabel;
    private Label wonLabel;

    private TextButton mainMenu;
    private TextButton playAgain;

    private boolean won;

    private int level;

    private Texture background;

    /** Overridden GameOverState constructor that takes in a GameStateManager class instance,
     * a boolean representing if the user won or lost, a final score integer, and a level integer.
     *
     * @param gsm GameStateManager class instance
     * @param won Boolean representing whether the user won or lost
     * @param finalScore Integer representing the user's final score after the game state change.
     * @param level Integer representing the level the user ended on.
     */
    GameOverState(GameStateManager gsm, boolean won, int finalScore, int level) {
        super(gsm);
        background = new Texture("backgrounds/main_menu_background.jpg");
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        this.won = won;
        this.level = level;

        gameLabel = new Label("You lose! Your final score is " + finalScore, skin);

        playAgain = new TextButton("Try Again", skin);
        table.add(gameLabel);
        table.row().padTop(20).padBottom(20);
        table.add(playAgain);
        table.row().padTop(20).padBottom(20);

        mainMenu = new TextButton("Main Menu", skin);
        if (won) {
            gameLabel.setText("You win! Your total score is " + finalScore + "!");
            playAgain.setText("Continue");
            table.row().padTop(20).padBottom(20);
        }
        table.add(mainMenu);
    }

    /** Method called to handle user clicks on the two buttons: "Try Again"/"Continue" and
     * "Main Menu"
     */
    @Override
    public void handleInput() {
        if (!won) {
            playAgain.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    gsm.set(new PlayState(gsm, level));
                }
            });
        }
        else if (level < 3) {
            playAgain.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    gsm.set(new PlayState(gsm, level + 1));
                }
            });
        }
        else if (level == 3) {
            playAgain.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    gsm.set(new LevelSelectState(gsm));
                }
            });
        }
        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new MenuState(gsm));
            }
        });
    }

    /** Method that is constantly called to then call handleInput.
     *
     * @param delta Time difference between two frames
     */
    @Override
    public void update(float delta) {
        handleInput();
    }

    /** Method that is called to render the background image and buttons onto the screen.
     *
     * @param sb SpriteBatch class instance to render objects
     */
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(background, 0, 0, BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
        sb.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /** Method called when there is a game state change from LevelSelectState to some other state
     * to dispose of any resources/assets.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        background.dispose();
    }
}
