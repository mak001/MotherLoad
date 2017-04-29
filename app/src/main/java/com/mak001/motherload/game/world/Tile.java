package com.mak001.motherload.game.world;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.mak001.motherload.game.helpers.Locatable;

/**
 * Created by Matthew on 3/22/2017.
 */

public class Tile extends Locatable {

    private TileType type;

    public Tile(int x, int y, TileType type) {
        super(x, y);
        this.type = type;
    }

    public void setTileType(TileType type) {
        this.type = type;
    }

    public TileType getTileType() {
        return type;
    }

    public Rect getImage() {
        return type.getImage();
    }

    @Override
    public String toString() {
        return location.getX() + ", " + location.getY() + " :: " + type;
    }

}
