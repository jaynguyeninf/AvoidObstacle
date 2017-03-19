package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.AssetPaths;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.configurations.GameConfig;
import com.mygdx.game.utilities.MyUtility;



public class HighScoreScreen extends ScreenAdapter {
    private static final Logger log = new Logger(HighScoreScreen.class.getName(), Logger.DEBUG);

    private final AvoidObstacleGame game;
    private final AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;

    public HighScoreScreen(AvoidObstacleGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT); //viewport automatically creates Ortho camera for us
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createUI();
    }

    private void createUI() {
        Table table = new Table();

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(AssetPaths.BACKGROUND_REGION);
        TextureRegion panelRegion = uiAtlas.findRegion(AssetPaths.PANEL);

        TextureRegion backRegion = uiAtlas.findRegion(AssetPaths.BACK);
        TextureRegion backPressedRegion = uiAtlas.findRegion(AssetPaths.BACK_PRESSED);

        //Label.LabelStyle basically word wrapper
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        //background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        //highscore text
        Label highScoreText = new Label("HIGHSCORE", labelStyle);

        //highscore label
        String highScoreString = GameManager.INSTANCE.getHighScoreString();
        Label highScoreLabel = new Label(highScoreString , labelStyle);

        //back button
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backRegion), new TextureRegionDrawable(backPressedRegion));
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        //setup table
        Table contentTable = new Table();
        contentTable.defaults().pad(20);
        contentTable.setBackground(new TextureRegionDrawable(panelRegion));

        contentTable.add(highScoreText).row();
        contentTable.add(highScoreLabel).row();
        contentTable.add(backButton);

        contentTable.center();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);
    }

    private void back(){
        log.debug("back()");
        game.setScreen(new MenuScreen(game));
    }

    @Override
    public void render(float delta) {
        MyUtility.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
