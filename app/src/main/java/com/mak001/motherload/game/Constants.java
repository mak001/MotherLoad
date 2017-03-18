package com.mak001.motherload.game;

import android.content.res.Resources;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Matthew on 2/25/2017.
 */

public class Constants {

    // used for both width and height 96 = (96 X 96)
    public static final int TILE_SIZE = 96;

    // move sped multiplier for player
    public static final float MOVE_SPEED = 10;

    public static final Vector2 GRAVITY = new Vector2(0, -9.8f);

    public static Resources RESOURCES;
    public static GamePanel GAME_PANEL;

}
