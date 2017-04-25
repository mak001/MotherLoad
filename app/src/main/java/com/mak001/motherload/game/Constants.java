package com.mak001.motherload.game;

import android.content.res.Resources;

import com.mak001.motherload.game.player.Player;
import com.mak001.motherload.game.world.World;

/**
 * Created by Matthew on 2/25/2017.
 */

public class Constants {

    // used for both width and height 96 = (96 X 96)
    public static final int TILE_SIZE = 96;
    public static final int PLAYER_SIZE = 88;

    // move sped multiplier for player
    public static final float MOVE_SPEED_X = 200;
    public static final float MOVE_SPEED_Y = 300;

    public static final float GRAVITY = -125f;
    public static final int MAX_FALL_SPEED = (int) (MOVE_SPEED_Y * TILE_SIZE);

    public static Resources RESOURCES;
    public static GamePanel GAME_PANEL;

    public static World WORLD;
    public static Player PLAYER;

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
}
