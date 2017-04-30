package com.mak001.motherload.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.mak001.motherload.game.world.World;

/**
 * Created by Matthew on 2/25/2017.
 */

public class Constants {

    public static final int ANIMATION_FRAME_TIME = 150;

    // tileset stuff
    public static final int BITMAP_TILE_SIZE = 32;
    public static Bitmap TILESET;

    // used for both width and height 96 = (96 X 96)
    public static final int TILE_SIZE = 96;
    public static final int PLAYER_SIZE = 88;

    // move sped multiplier for player
    public static final float MOVE_SPEED_X = 150;
    public static final float MOVE_SPEED_Y = 250;

    public static final float GRAVITY = 100f;
    public static final int MAX_FALL_SPEED = (int) (GRAVITY * TILE_SIZE);

    public static final int SKY_COLOR = Color.rgb(0, 124, 255);

    public static Resources RESOURCES;
    public static GamePanel GAME_PANEL;

    public static World WORLD;

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int TILES_IN_SCREEN_WIDTH;
    public static int TILES_IN_SCREEN_HEIGHT;
}
