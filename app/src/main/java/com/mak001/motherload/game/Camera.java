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
    }
}
