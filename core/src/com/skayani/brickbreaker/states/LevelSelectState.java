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

/** LevelSelectState that is instantiated when the user clicks "New Game" from the MenuState.
 *
 */

public class LevelSelectState extends State {
    private Stage stage;
    private Skin skin;

    private Label screenTitle;

    // Text-buttons for the three game levels
    private TextButton levelOne;
    private TextButton levelTwo;
    private TextButton levelThree;

    private Texture background;

    /** LevelSelectState class constructor that takes in a GameStateManager class instance.
     *
     * @param gsm GameStateManager class instance
     */
    LevelSelectState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("backgrounds/main_menu_background.jpg");
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.center();

        screenTitle = new Label("Select Level", skin);

        // Initializing different TextButtons
        levelOne = new TextButton("1", skin);
        levelTwo = new TextButton("2", skin);
        levelThree = new TextButton("3", skin);

        // Table formatting
        table.add(screenTitle).center();
        table.row().pad(20).padBottom(20);
        table.add(levelOne);
        table.row().padTop(20).padBottom(20);
        table.add(levelTwo);
        table.row().padTop(20).padBottom(20);
        table.add(levelThree);
    }

    /** Method called to handle user clicks on the different level buttons.
     *
     */
    @Override
    public void handleInput() {
        levelOne.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new PlayState(gsm, 1));
            }
        });
        levelTwo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new PlayState(gsm, 2));
            }
        });
        levelThree.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new PlayState(gsm, 3));
            }
        });
    }

    /** Method constantly called that calls handleInput in its body to handle user clicks.
     *
     * @param delta Time difference between two frames
     */
    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(background, 0, 0, BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
        sb.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /** Method called when there is a game state change from LevelSelectState to PlayState to
     * deallocate asset resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        skin.dispose();
    }
}
