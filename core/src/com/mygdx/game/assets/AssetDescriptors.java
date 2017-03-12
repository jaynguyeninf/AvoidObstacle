package com.mygdx.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Vu on 2/21/2017.
 */

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT = new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);
    public static final AssetDescriptor<TextureAtlas> GAMEPLAY_ATLAS = new AssetDescriptor<TextureAtlas>(AssetPaths.GAMEPLAY_ATLAS, TextureAtlas.class);
    public static final AssetDescriptor<TextureAtlas> UI_ATLAS = new AssetDescriptor<TextureAtlas>(AssetPaths.UI_ATLAS, TextureAtlas.class);


    private AssetDescriptors(){}
}
