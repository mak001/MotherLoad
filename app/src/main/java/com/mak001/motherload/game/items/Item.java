package com.mak001.motherload.game.items;

import android.graphics.Bitmap;

/**
 * Created by Matthew on 2/23/2017.
 */

public enum Item {

    BRONZE(null);

    private final int value;
    private final int weight;
    private final boolean sellable;
    private final Bitmap image;

    Item() {
        this(null);
    }

    Item(Bitmap image) {
        this(0, image);
    }

    Item(int value, Bitmap image) {
        this(value, false, image);
    }

    Item(int value, boolean sellable, Bitmap image) {
        this(value, 0, sellable, image);
    }

    Item(int value, int weight, boolean sellable, Bitmap image) {
        this.value = value;
        this.weight = weight;
        this.sellable = sellable;
        this.image = image;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isSellable() {
        return sellable;
    }

    public Bitmap getImage() {
        return image;
    }

}
