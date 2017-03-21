package com.mygdx.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.configurations.LevelDifficulty;

/**
 * Created by Jay Nguyen on 3/17/2017.
 */

public class GameManager {
    //on desktop: xml file is stored in user/.prefs/

    public static final GameManager INSTANCE = new GameManager(); //can only be 1 instance of this class --make public or use getter

    private static final String HIGH_SCORE_KEY = "High Score";
    private static final String DIFFICULTY_KEY = "Difficulty";


    private Preferences prefs; //simple way to store data
    private int highScore;

    private LevelDifficulty levelDifficulty = LevelDifficulty.MEDIUM;


    private GameManager() {
        prefs = Gdx.app.getPreferences(AvoidObstacleGame.class.getSimpleName()); //get name for the xml file
        highScore = prefs.getInteger(HIGH_SCORE_KEY, 0);                        // String key, default value
        String difficultyName = prefs.getString(DIFFICULTY_KEY, LevelDifficulty.MEDIUM.name());
        levelDifficulty = levelDifficulty.valueOf(difficultyName);
    }

    public void updateHighScore(int score) {
        //if score is less than high score than there's no need to update it
        if (score < highScore) {
            return;
        }

        highScore = score; //set high score to highest score

        prefs.putInteger(HIGH_SCORE_KEY, highScore); //save score (key, value)
        prefs.flush(); //make sure preferences are persisted/saved

    }

    public String getHighScoreString() {
        return String.valueOf(highScore); //convert int to String
    }

    public LevelDifficulty getLevelDifficulty() {
        return levelDifficulty;
    }

    public void updateDifficulty(LevelDifficulty newLevelDifficulty) {
        if(levelDifficulty == newLevelDifficulty){
            return;
        }

        levelDifficulty = newLevelDifficulty;
        prefs.putString(DIFFICULTY_KEY, levelDifficulty.name());
        prefs.flush();
    }
}
