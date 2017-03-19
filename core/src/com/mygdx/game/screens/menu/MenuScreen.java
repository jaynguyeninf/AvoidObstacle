package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.AssetPaths;
import com.mygdx.game.configurations.GameConfig;
import com.mygdx.game.screens.game.GameScreen;
import com.mygdx.game.utilities.MyUtility;


public class MenuScreen extends ScreenAdapter {
    private static final Logger log = new Logger(MenuScreen.class.getName(), Logger.DEBUG);

    private AvoidObstacleGame game;
    private AssetManager assetManager;
    private Stage stage;
    private Viewport viewport;

    public MenuScreen(AvoidObstacleGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override

    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        initUI();
    }

    private void initUI() {
        Table table = new Table();

        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(AssetPaths.BACKGROUND_REGION);
        TextureRegion panelRegion = uiAtlas.findRegion(AssetPaths.PANEL);

        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        //play button
        ImageButton playButton = createButton(uiAtlas, AssetPaths.PLAY, AssetPaths.PLAY_PRESSED);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });




        //high score button
        ImageButton highScoreButton = createButton(uiAtlas, AssetPaths.HIGH_SCORE, AssetPaths.HIGH_SCORE_PRESSED);
        highScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHighScore();
            }
        });

        //options button
        ImageButton optionButton = createButton(uiAtlas, AssetPaths.OPTIONS, AssetPaths.OPTIONS_PRESSED);
        optionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptions();
            }
        });


        //quit button


        //setup table
        Table buttonTable = new Table();
        buttonTable.defaults().pad(20);
        buttonTable.setBackground(new TextureRegionDrawable(panelRegion));

        buttonTable.add(playButton).row();
        buttonTable.add(highScoreButton).row();
        buttonTable.add(optionButton).row();

        buttonTable.center();

        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();
        stage.addActor(table);
    }

    //create ImageButton
    private static ImageButton createButton(TextureAtlas atlas, String upRegionName, String downRegionName){
        TextureRegion upRegion = atlas.findRegion(upRegionName);
        TextureRegion downRegion = atlas.findRegion(downRegionName);

        return new ImageButton(new TextureRegionDrawable(upRegion), new TextureRegionDrawable(downRegion));
    }

    private void play(){
        log.debug("play()");
        game.setScreen(new GameScreen(game));
    }

    private void showHighScore(){
        log.debug("showHighScore()");
        game.setScreen(new HighScoreScreen(game));
    }

    private void showOptions(){
        log.debug("showOptions()");
    }


    @Override
    public void render(float delta) {
        MyUtility.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
