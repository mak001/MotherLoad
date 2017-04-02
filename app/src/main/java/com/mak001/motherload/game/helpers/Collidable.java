package com.mak001.motherload.game.helpers;

import android.graphics.Rect;

import com.mak001.motherload.game.Constants;

/**
 * Created by Matthew on 3/22/2017.
 */

public abstract class Collidable extends Locatable {

    protected Rect collider;
    protected boolean canCollide = true;

    public Collidable(int x, int y) {
        this(x, y, Constants.TILE_SIZE);
    }

    public Collidable(int x, int y, int size) {
        this(x, y, size, size);
    }

    public Collidable(int x, int y, int width, int height) {
        super(x, y);
        collider = new Rect(x, y, x + width, y + height);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        collider.offsetTo((int) x, collider.top);
    }

    @Override
    public void setY(float y) {
        super.setY(y);

        collider.offsetTo(collider.left, (int) y);
    }

    @Override
    public void setPos(float x, float y) {
        super.setPos(x, y);
        collider.offsetTo((int) x, (int) y);
    }

    public Rect getCollider() {
        return collider;
    }

    public boolean canCollide() {
        return canCollide;
    }

    public boolean isColliding(Collidable collidable) {
        if (collidable == null || !collidable.canCollide() || !canCollide)
            return false;

        Rect other = collidable.getCollider();

        return Rect.intersects(collider, other);
    }

    /**
     * -1 = none
     * 0  = top
     * 1  = right
     * 2  = bottom
     * 3  = left
     *
     * @param collidable
     * @return
     */
    public int getColidingSide(Collidable collidable) {
        if (!isColliding(collidable)) {
            return -1;
        }

        Rect other = collidable.getCollider();

        if (collider.left <= other.right) {
            return 3;
        } else if (collider.right <= other.left) {
            return 1;
        } else if (collider.top <= other.bottom) {
            return 0;
        } else if (collider.bottom <= other.top) {
            return 2;
        }

        return -1;
    }

}
