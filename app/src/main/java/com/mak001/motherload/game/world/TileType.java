package com.mak001.motherload.game.world;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mak001.motherload.R;
import com.mak001.motherload.game.Constants;

/**
 * Created by Matthew on 2/23/2017.
 */

public enum TileType {

    AIR(0.05f),
    DIRT(0.85f, new int[]{R.drawable.tiles_dirt, R.drawable.tiles_dirt_2});

    private final int minDepth, maxDepth;
    private final int hardness;
    private final int damage;

    private final float chance;

    private final Bitmap[] images;


    TileType(float chance) {
        this(chance, -1);
    }

    TileType(float chance, int imageID) {
        this(0, chance, imageID);
    }

    TileType(float chance, int[] imageIDs) {
        this(0, chance, imageIDs);
    }

    TileType(int minDepth, float chance, int imageID) {
        this(minDepth, 0, 0, chance, imageID);
    }

    TileType(int minDepth, float chance, int[] imageIDs) {
        this(minDepth, 0, 0, chance, imageIDs);
    }

    TileType(int minDepth, int maxDepth, int hardness, float chance, int imageID) {
        this(minDepth, maxDepth, hardness, 0, chance, imageID);
    }

    TileType(int minDepth, int maxDepth, int hardness, float chance, int[] imageIDs) {
        this(minDepth, maxDepth, hardness, 0, chance, imageIDs);
    }

    TileType(int minDepth, int maxDepth, int hardness, int damage, float chance, int imageID) {
        this(minDepth, maxDepth, hardness, damage, chance, new int[]{imageID});
    }

    TileType(int minDepth, int maxDepth, int hardness, int damage, float chance, int[] imageIDs) {

        this.minDepth = minDepth;
        this.hardness = hardness;
        this.maxDepth = maxDepth;
        this.damage = damage;
        this.chance = chance;

        images = new Bitmap[imageIDs.length];

        for (int i = 0; i < imageIDs.length; i++) {
            if (imageIDs[i] == -1) {
                images[i] = null;
            } else {
                Bitmap image = BitmapFactory.decodeResource(Constants.RESOURCES, imageIDs[i]);
                images[i] = Bitmap.createScaledBitmap(image, Constants.TILE_SIZE, Constants.TILE_SIZE, false);
            }
        }
    }

    public static TileType getIndex(int i) {
        return TileType.values()[i];
    }

    public static TileType getDefault() {
        return TileType.DIRT;
    }

    public int getImageID() {
        return (int) (Math.random() * images.length);
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

    public float getChance() {
        return chance;
    }

    public Bitmap getImage(int index) {
        if (images.length <= index)
            index = images.length - 1;
        return images[index];
    }

}
