package com.skayani.brickbreaker.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skayani.brickbreaker.BrickBreaker;

/** PreferencesState (extending upon abstract State class) for allowing user to adjust
 * game settings.
 */
public class PreferencesState extends State {
    // Main stage member variable
    private Stage stage;

    // Skin class to utilize third-party button images
    private Skin skin;

    // Labels, Buttons, Sliders, and Textboxes for allowing user input
    private Label musicEnabledLabel;
    private CheckBox musicEnableCheckbox;
    private Label musicVolumeLabel;
    private Slider musicVolumeSlider;
    private Label soundEnabledLabel;
    private CheckBox soundEnabledCheckbox;
    private Label soundVolumeLabel;
    private Slider soundVolumeSlider;
    private TextButton mainMenu;
    private TextButton playGame;
    private Texture background;

    // True if user went from PlayState to PreferencesState
    private boolean continuingGame;

    /** PreferencesState constructor, taking a GameStateManager instance as an argument
     *
     * @param gsm GameStateManager class instance
     */
    PreferencesState(GameStateManager gsm) {
        super(gsm);

        // Setting background image
        background = new Texture("backgrounds/main_menu_background.jpg");

        continuingGame = false;

        // Setting stage and initializing skin to third-party skin
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        Gdx.input.setInputProcessor(stage);

        // Initializing tables
        Table optionsTable = new Table();
        Table buttonsTable = new Table();
        optionsTable.setFillParent(true);

        musicEnabledLabel = new Label("Music Enabled", skin);
        musicEnableCheckbox = new CheckBox(null, skin);
        if (gsm.musicOn) {
            musicEnableCheckbox.toggle();
        }

        musicVolumeLabel = new Label("Music Volume", skin);
        musicVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        musicVolumeSlider.setValue(gsm.musicVolume);

        soundEnabledLabel = new Label("Sound Enabled", skin);
        soundEnabledCheckbox = new CheckBox(null, skin);
        if (gsm.soundOn) {
            soundEnabledCheckbox.toggle();
        }

        soundVolumeLabel = new Label("Sound Volume", skin);
        soundVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        soundVolumeSlider.setValue(gsm.soundVolume);

        mainMenu = new TextButton("Main Menu", skin);

        if (gsm.getSize() > 1) {
            continuingGame = true;
            playGame = new TextButton("Continue", skin);
        }
        else {
            playGame = null;
        }

        // Formatting optionsTable
        optionsTable.defaults().expand().fill();
        optionsTable.add(musicEnabledLabel).padLeft(60);
        optionsTable.add(musicEnableCheckbox).uniformX().uniformY();
        optionsTable.row();
        optionsTable.add(musicVolumeLabel).padLeft(60);
        optionsTable.add(musicVolumeSlider).uniformX().uniformY();
        optionsTable.row();
        optionsTable.add(soundEnabledLabel).padLeft(60);
        optionsTable.add(soundEnabledCheckbox).uniformX().uniformY();
        optionsTable.row();
        optionsTable.add(soundVolumeLabel).padLeft(60);
        optionsTable.add(soundVolumeSlider).uniformX().uniformY();
        optionsTable.row();

        if (continuingGame) {
            buttonsTable.add(playGame).padBottom(40);
            buttonsTable.row();
        }
        buttonsTable.add(mainMenu);

        // Combining optionsTable and buttonsTable into optionsTable
        optionsTable.add(buttonsTable).colspan(3);
        stage.addActor(optionsTable);
    }

    /** Method that handles user inputs for setting musicVolume or soundVolume on/off, as well as
     * adjusting the music and sound volume.
     */
    @Override
    public void handleInput() {
        gsm.musicOn = musicEnableCheckbox.isChecked();
        gsm.musicVolume = musicVolumeSlider.getValue();
        gsm.soundOn = soundEnabledCheckbox.isChecked();
        gsm.soundVolume = soundVolumeSlider.getValue();

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.set(new MenuState(gsm));
            }
        });

        if (continuingGame) {
            playGame.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (gsm.getSize() > 1) {
                        gsm.decSize();
                        gsm.pop();
                    }
                }
            });
        }
    }

    /** Method that constantly calls handleInput to check for setting changes and then setting
     * boolean variables according to those changes.
     *
     * @param delta Time difference between two frames
     */
    @Override
    public void update(float delta) {
        handleInput();
        if (!gsm.musicOn) {
            gsm.playMusic.pause();
        }
        else if (!gsm.playMusic.isPlaying()) {
            gsm.playMusic.play();
        }
        if (!gsm.soundOn) {
            gsm.scoreSound.setVolume(0);
        }
        else if (gsm.scoreSound.getVolume() == 0) {
            gsm.scoreSound.setVolume(gsm.soundVolume);
        }
        gsm.playMusic.setVolume(gsm.musicVolume);
        gsm.scoreSound.setVolume(gsm.soundVolume);
    }

    /** Method that renders background onto the screen as well as draw the optionsTable, consisting
     * of various clickable/pressable buttons.
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

    /** Method called when switched from PreferencesState to another game state to free memory
     * allocation.
     */
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        skin.dispose();
    }
}
