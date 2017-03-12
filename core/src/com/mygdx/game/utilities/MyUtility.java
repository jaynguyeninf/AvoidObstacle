package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Vu on 1/26/2017.
 */

public class MyUtility {
    private MyUtility(){
    }

    public static void clearScreen(){
        clearScreen(Color.BLACK);
    }
    public static void clearScreen(Color color){
        Gdx.gl.glClearColor(color.r,color.g,color.b,color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
