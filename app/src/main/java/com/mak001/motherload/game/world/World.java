package com.mak001.motherload.game.world;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.Renderable;

/**
 * Created by Matthew on 2/25/2017.
 */

public class World implements Renderable {
    private float[][] tiles;
    private int lastGeneratedLayer = 0;


    public World() {

        tiles = new float[25][500];

        generate(tiles.length);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < lastGeneratedLayer; y++) {
                TileType tile = TileType.getIndex((int) tiles[x][y]);
                if (tile.getImage() != null) {
                    canvas.drawBitmap(tile.getImage(tiles[x][y]), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, paint);
                }
            }
        }
    }

    public int getTileAt(int x, int y) {
        if (x >= 0 && y >= 0) {
            return (int) tiles[x][y];
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
        // TODO - generate TileTypes within their given range
        for (int x = 0; x < tiles.length; x++) {
            for (int y = lastGeneratedLayer; y < lastGeneratedLayer + height; y++) {

                // set the default TileType
                tiles[x][y] = TileType.getDefault().getID();

                float chance = (float) Math.random();

                for (int i = 0; i < TileType.values().length; i++) {
                    TileType curr = TileType.getIndex(i);

                    // skips default TitleType (so it does not override previous types)
                    if (curr != TileType.getDefault()) {
                        if (chance <= curr.getChance()) {
                            tiles[x][y] = curr.getID();

                        }
                    }
                }
            }
        }

        lastGeneratedLayer += height;
    }
}
