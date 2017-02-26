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
        this.tileWidth = TileType.DIRT.getImage().getWidth();
        this.tileHeight = TileType.DIRT.getImage().getHeight();

        tiles = new int[25][500];

        generate(tiles.length);
    }

    private boolean drew = false;

    @Override
    public void draw(Canvas canvas) {
        // TODO
        if (!drew) {
            Paint paint = new Paint();
            for (int x = 0; x < tiles.length; x++) {
                for (int y = 0; y < lastGeneratedLayer; y++) {
                    TileType tile = TileType.getIndex(tiles[x][y]);
                    if (tile.getImage() != null) {
                        canvas.drawBitmap(tile.getImage(), x * tileWidth, y * tileHeight, paint);
                    } else {
                        System.out.println("image null at: " + x + ", " + y);
                    }
                }
            }
        }
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

        for (int x = 0; x < tiles.length; x++) {
            for (int y = lastGeneratedLayer; y < lastGeneratedLayer + height; y++) {
                tiles[x][y] = TileType.DIRT.ordinal();


            }
        }

        lastGeneratedLayer += height;
    }
}
