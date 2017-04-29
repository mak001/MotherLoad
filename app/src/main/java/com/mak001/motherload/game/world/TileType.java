package com.mak001.motherload.game.world;

import android.graphics.Rect;

import com.mak001.motherload.game.Constants;

/**
 * Created by Matthew on 2/23/2017.
 */
public enum TileType {
    AIR(0.05f, 0), DIRT(0.85f, 1);

    // variables for tile
    private final int minDepth, maxDepth;
    private final int hardness;
    private final int damage;
    private final float chance;
    private final int imageID;

    private Rect image;

    TileType(float chance, int imageID) {
        this(0, 0, 0, 0, chance, imageID);
    }

    TileType(int minDepth, int maxDepth, int hardness, int damage, float chance, int imageID) {

        this.minDepth = minDepth;
        this.maxDepth = maxDepth;

        this.hardness = hardness;
        this.damage = damage;
        this.chance = chance;

        this.imageID = imageID;
    }

    public static TileType getDefault() {
        return DIRT;
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

    public Rect getImage() {
        if (image != null) return image;

        int TILES_IN_X = Constants.TILESET.getWidth() / Constants.BITMAP_TILE_SIZE;

        int x = (imageID % TILES_IN_X) * Constants.BITMAP_TILE_SIZE;
        int y = ((int) Math.floor(imageID / TILES_IN_X)) * Constants.BITMAP_TILE_SIZE;

        image = new Rect(x, y, x + Constants.BITMAP_TILE_SIZE, y + Constants.BITMAP_TILE_SIZE);

        return image;
    }
}