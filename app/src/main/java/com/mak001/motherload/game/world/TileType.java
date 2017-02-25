package com.mak001.motherload.game.world;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mak001.motherload.R;
import com.mak001.motherload.game.Constants;

/**
 * Created by Matthew on 2/23/2017.
 */

public enum TileType {

    AIR(),
    DIRT(R.drawable.tiles_dirt);

    private final int id;
    private final int depth;
    private final int hardness;
    private final int weight;
    private final int damage;
    private final Bitmap image;
    TileType() {
        this(-1);
    }

    TileType(int imageID) {
        this(0, imageID);
    }

    TileType(int weight, int imageID) {
        this(weight, 0, 0, 0, imageID);
    }

    TileType(int weight, int depth, int hardness, int damage, int imageID) {
        this.id = this.ordinal();
        this.depth = depth;
        this.hardness = hardness;
        this.weight = weight;
        this.damage = damage;

        if (imageID == -1) {
            this.image = null;
        } else {
            this.image = BitmapFactory.decodeResource(Constants.RESOURCES, imageID);
        }
        //R.drawable;
    }

    public static TileType getIndex(int i) {
        return TileType.values()[i];
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
