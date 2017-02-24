package com.mak001.motherload.game.world;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Matthew on 2/23/2017.
 */

public enum TileType {

    AIR(),
    DIRT();

    private final int id;
    private final int depth;
    private final int hardness;
    private final int weight;
    private final int damage;
    private final Bitmap image;

    TileType() {
        this(null);
    }

    TileType(Bitmap image) {
        this(0, image);
    }

    TileType(int weight, Bitmap image) {
        this(weight, 0, 0, 0, image);
    }

    TileType(int weight, int depth, int hardness, int damage, Bitmap image) {
        this.id = this.ordinal();
        this.depth = depth;
        this.hardness = hardness;
        this.weight = weight;
        this.damage = damage;
        this.image = image;
    }

    public int getID() {
        return id;
    }

    public int getMinDepth() {
        return depth;
    }

    public int getDamage() {
        return damage;
    }

    public int getWeight() {
        return weight;
    }

    public int getHardness() {
        return hardness;
    }

    public Bitmap getImage() {
        return image;
    }

}
