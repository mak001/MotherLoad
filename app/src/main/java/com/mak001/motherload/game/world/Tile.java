package com.mak001.motherload.game.world;

import android.graphics.Rect;

import com.mak001.motherload.game.helpers.Locatable;

/**
 * Created by Matthew on 3/22/2017.
 */

public class Tile extends Locatable {

    private TileType type;

    Tile(int x, int y, TileType type) {
        super(x, y);
        this.type = type;
    }

    void setTileType(TileType type) {
        this.type = type;
    }

    TileType getTileType() {
        return type;
    }

    Rect getImage() {
        return type.getImage();
    }

    @Override
    public String toString() {
        return location.getX() + ", " + location.getY() + " :: " + type;
    }

}
