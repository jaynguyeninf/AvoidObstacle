package com.mygdx.game.screens.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.AvoidObstacleGame;

/**
 * Created by Vu on 2/6/2017.
 */

public class GameScreen implements Screen {
    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);

    private final AvoidObstacleGame game;
    private final AssetManager assetManager;
    private GameController gameController;
    private GameRenderer gameRenderer;

    public GameScreen(AvoidObstacleGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        log.debug("show");
        gameController = new GameController();
        gameRenderer = new GameRenderer(game.getBatch(), assetManager, gameController);
    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        log.debug("show");
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }
}
