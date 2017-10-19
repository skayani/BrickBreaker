package com.skayani.brickbreaker.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skayani.brickbreaker.BrickBreaker;

/** MenuState class that is instatiated when the game is first launched.
 *
 */

public class MenuState extends State {
    private Stage stage;
    private Skin skin;

    // Text-buttons for various user input
    private TextButton newGame;
    private TextButton preferences;
    private TextButton exit;
    private Texture background;

    private BitmapFont bitmapFont;

    /** MenuState constructor that takes in GameStateManager class instance
     *
     * @param gsm GameStateManager class instance
     */
    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
        bitmapFont = new BitmapFont();
        background = new Texture("backgrounds/main_menu_background.jpg");
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        newGame = new TextButton("New Game", skin);
        preferences = new TextButton("Options", skin);
        exit = new TextButton("Exit", skin);

        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(preferences).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();
    }

    /** Method called to handle user clicks/presses on the various buttons.
     *
     */
    @Override
    public void handleInput() {
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new LevelSelectState(gsm));
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new PreferencesState(gsm));
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    /** Method constantly called that then calls handleInput to record button changes.
     *
     * @param delta Time difference between two frames
     */
    @Override
    public void update(float delta) {
        handleInput();
    }

    /** Method called to render background, copyright message, and buttons to the MenuState
     * screen.
     *
     * @param sb SpriteBatch class instance to render objects
     */
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(background, 0, 0, BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
        bitmapFont.draw(sb, "Background Image Created by GarryKillian - Freepik.com",
                60, 20);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        sb.end();
    }

    /** Method called when there is a state change from MenuState to another state, to
     * dispose/deallocate any resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        background.dispose();
    }
}
