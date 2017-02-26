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
    private final int minDepth, maxDepth;
    private final int hardness;
    private final int damage;
    private final Bitmap image;

    TileType() {
        this(-1);
    }

    TileType(int imageID) {
        this(0, imageID);
    }

    TileType(int minDepth, int imageID) {
        this(minDepth, 0, 0, imageID);
    }

    TileType(int minDepth, int maxDepth, int hardness, int imageID) {
        this(minDepth, maxDepth, hardness, 0, imageID);
    }

    TileType(int minDepth, int maxDepth, int hardness, int damage, int imageID) {
        this.id = this.ordinal();
        this.minDepth = minDepth;
        this.hardness = hardness;
        this.maxDepth = maxDepth;
        this.damage = damage;

        if (imageID == -1) {
            this.image = null;
        } else {
            Bitmap image = BitmapFactory.decodeResource(Constants.RESOURCES, imageID);
            this.image = Bitmap.createScaledBitmap(image, 96, 96, false);
            //this.image = image;
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
        return minDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public int getDamage() {
        return damage;
    }

    public int getHardness() {
        return hardness;
    }

    public Bitmap getImage() {
        return image;
    }

}
