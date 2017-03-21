package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.AssetPaths;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.configurations.GameConfig;
import com.mygdx.game.configurations.LevelDifficulty;

/**
 * Created by Jay Nguyen on 3/20/2017.
 */

public class OptionsScreen extends ScreenAdapter {

    private static final Logger log = new Logger(OptionsScreen.class.getName(), Logger.DEBUG);
    private final AvoidObstacleGame game;
    private final AssetManager assetManager;

    private Stage stage;
    private Viewport viewport;
    private Image checkMarkImage;

    public OptionsScreen(AvoidObstacleGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.defaults().pad(15);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        TextureAtlas uiAtlas = assetManager.get(AssetDescriptors.UI_ATLAS);
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(AssetPaths.BACKGROUND_REGION);
        Image background = new Image(backgroundRegion);

        Label label = new Label("DIFFICULTY", labelStyle);
        label.setPosition(GameConfig.HUD_WIDTH/2, GameConfig.HUD_HEIGHT/2 + 180, Align.center);

        final ImageButton easyButton = createButton(uiAtlas, AssetPaths.EASY);
        easyButton.setPosition(GameConfig.HUD_WIDTH/2, GameConfig.HUD_HEIGHT/2 + 90, Align.center);
        easyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkMarkImage.setY(easyButton.getY() + 25);
                GameManager.INSTANCE.updateDifficulty(LevelDifficulty.EASY);
            }
        });

        final ImageButton mediumButton = createButton(uiAtlas, AssetPaths.MEDIUM);
        mediumButton.setPosition(GameConfig.HUD_WIDTH/2, GameConfig.HUD_HEIGHT/2, Align.center);
        mediumButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkMarkImage.setY(mediumButton.getY() + 25);
                GameManager.INSTANCE.updateDifficulty(LevelDifficulty.MEDIUM);
            }
        });

        final ImageButton hardButton = createButton(uiAtlas, AssetPaths.HARD);
        hardButton.setPosition(GameConfig.HUD_WIDTH/2, GameConfig.HUD_HEIGHT/2 - 90, Align.center);
        hardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkMarkImage.setY(hardButton.getY()+25);
                GameManager.INSTANCE.updateDifficulty(LevelDifficulty.HARD);
            }
        });

        TextureRegion checkMarkRegion = uiAtlas.findRegion(AssetPaths.CHECK_MARK);
        checkMarkImage = new Image(new TextureRegionDrawable(checkMarkRegion));
        checkMarkImage.setPosition(mediumButton.getX() + 50, mediumButton.getY() + 40, Align.center); //position at medium

        LevelDifficulty levelDifficulty = GameManager.INSTANCE.getLevelDifficulty();

        //check checkMarkButton's positioning
        if(levelDifficulty.isEasy()){
            checkMarkImage.setY(easyButton.getY() + 25);
        } else if(levelDifficulty.isHard()){
            checkMarkImage.setY(hardButton.getY() + 25);
        } //medium is default, no need to check

        ImageButton backButton = new ImageButton(
                new TextureRegionDrawable(uiAtlas.findRegion(AssetPaths.BACK)),
                new TextureRegionDrawable(uiAtlas.findRegion(AssetPaths.BACK_PRESSED))
        );

        backButton.setPosition(GameConfig.HUD_WIDTH/2, GameConfig.HUD_HEIGHT/2 - 180, Align.center);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        stage.addActor(background);
        stage.addActor(label);
        stage.addActor(easyButton);
        stage.addActor(mediumButton);
        stage.addActor(hardButton);
        stage.addActor(checkMarkImage);
        stage.addActor(backButton);

    }

    private ImageButton createButton(TextureAtlas atlas, String regionName) {
        TextureRegion region = atlas.findRegion(regionName);
        return new ImageButton(new TextureRegionDrawable(region));
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

    }

    private void back(){
        log.debug("back()");
        game.setScreen(new MenuScreen(game));
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
