package com.mak001.motherload.game.world;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mak001.motherload.R;
import com.mak001.motherload.game.Constants;

import java.util.Random;

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

    private final Bitmap[] images;


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
        this(minDepth, maxDepth, hardness, damage, new int[]{imageID});
    }

    TileType(int minDepth, int maxDepth, int hardness, int damage, int[] imageIDs) {

        this.id = this.ordinal();
        this.minDepth = minDepth;
        this.hardness = hardness;
        this.maxDepth = maxDepth;
        this.damage = damage;

        images = new Bitmap[imageIDs.length];

        for (int i = 0; i < imageIDs.length; i++) {
            if (imageIDs[i] == -1) {
                images[i] = null;
            } else {
                Bitmap image = BitmapFactory.decodeResource(Constants.RESOURCES, imageIDs[i]);
                images[i] = Bitmap.createScaledBitmap(image, 96, 96, false);
            }
        }
    }

    public static TileType getIndex(int i) {
        return TileType.values()[i];
    }

    public float getID() {
        return id + ((int) (Math.random() * images.length) / 100f);
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
        return images[0];
    }

    public Bitmap getImage(int index) {
        return images[index];
    }

}
