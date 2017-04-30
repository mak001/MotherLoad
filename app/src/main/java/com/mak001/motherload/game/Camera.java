package com.mak001.motherload.game;

import com.mak001.motherload.game.helpers.Locatable;

/**
 * Created by Matthew on 3/20/2017.
 */

public class Camera extends Locatable {

    public Camera() {
        this(0, 0);
    }

    public Camera(int x, int y) {
        super(x, y);

        Constants.TILES_IN_SCREEN_WIDTH = (int) Math.ceil(Constants.SCREEN_WIDTH / Constants.TILE_SIZE) + 1;
        Constants.TILES_IN_SCREEN_HEIGHT = (int) Math.ceil(Constants.SCREEN_HEIGHT / Constants.TILE_SIZE) + 1;
    }
}
