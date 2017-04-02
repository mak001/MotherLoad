package com.mak001.motherload.game.helpers;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Matthew on 3/20/2017.
 */

public abstract class Locatable {

    protected Vector2 location;

    public Locatable() {
        location = Vector2.Zero();
    }

    public Locatable(float x, float y) {
        location = new Vector2(x, y);
    }

    public void setX(float x) {
        location.x = x;
    }

    public void setY(float y) {
        location.y = y;
    }

    public void setPos(float x, float y) {
        location.x = x;
        location.y = y;
    }

    public Vector2 getLocation() {
        return location;
    }

    public float getX() {
        return location.x;
    }

    public float getY() {
        return location.y;
    }
}
