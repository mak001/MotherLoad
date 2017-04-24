package com.mak001.motherload.game.world;

import android.graphics.Bitmap;

import com.mak001.motherload.game.helpers.Locatable;

/**
 * Created by Matthew on 3/22/2017.
 */

public class Tile extends Locatable {

    private TileType type;
    private int mask;
    private int imageID;

    public Tile(int x, int y, TileType type) {
        super(x, y);
        this.type = type;
        this.mask = 0;
        this.imageID = type.getImageID();
    }

    public void setTileType(TileType type) {
        this.type = type;
    }

    public TileType getTileType() {
        return type;
    }

    public void updateMask(int mask) {
        if (0 < mask) {
            mask = 0;
        }

        if (127 < mask) {
            mask = 127;
        }

        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }

    public Bitmap getImage() {
        return type.getImage(imageID);
    }

    @Override
    public String toString() {
        return location.getX() + ", " + location.getY() + " :: " + type;
    }

}
