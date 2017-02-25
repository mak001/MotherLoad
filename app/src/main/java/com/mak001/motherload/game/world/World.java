package com.mak001.motherload.game.world;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.mak001.motherload.game.Renderable;

/**
 * Created by Matthew on 2/25/2017.
 */

public class World implements Renderable {

    private final int tileWidth, tileHeight;
    private int[][] tiles;
    private int lastGeneratedLayer = 0;

    public World() {
        this(32, 32);
    }

    public World(int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        tiles = new int[25][500];

        generate(tiles.length);
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO
        Paint paint = new Paint();
    }

    public int getTileAt(int x, int y) {
        if (x >= 0 && y >= 0) {
            return tiles[x][y];
        } else {
            // 0 is air
            return 0;
        }
    }

    public TileType getTileTypeAt(int x, int y) {
        int index = getTileAt(x, y);

        return TileType.getIndex(index);
    }

    public void generate(int height) {
        // lastGeneratedLayer


        lastGeneratedLayer += height;
    }
}
