package com.mygdx.game.configurations;



public enum LevelDifficulty {

    EASY(GameConfig.OBSTACLE_EASY_SPEED),
    MEDIUM(GameConfig.OBSTACLE_MEDIUM_SPEED),
    HARD(GameConfig.OBSTACLE_HARD_SPEED);

    private final float obstacleSpeed;

    LevelDifficulty(float obstacleSpeed) {
        this.obstacleSpeed = obstacleSpeed;
    }

    public float getObstacleSpeed() {
        return obstacleSpeed;
    }
}
