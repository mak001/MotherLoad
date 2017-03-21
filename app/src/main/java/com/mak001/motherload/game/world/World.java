package com.mak001.motherload.game.world;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.mak001.motherload.game.Camera;
import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.Methods;
import com.mak001.motherload.game.helpers.Renderable;

/**
 * Created by Matthew on 2/25/2017.
 */

public class World implements Renderable {
    private float[][] tiles;
    private int lastGeneratedLayer = 0;

    private final Camera camera;

    public World(Camera camera) {
        this.camera = camera;

        tiles = new float[25][500];
        generate(tiles.length);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < lastGeneratedLayer; y++) {

                if (Methods.between(Math.floor(camera.getX() - Constants.TILE_SIZE), Math.ceil(camera.getX() + Constants.SCREEN_WIDTH + Constants.TILE_SIZE), x * Constants.TILE_SIZE) &&
                        Methods.between(Math.floor(camera.getY() - Constants.TILE_SIZE), Math.ceil(camera.getY() + Constants.SCREEN_HEIGHT + Constants.TILE_SIZE), y * Constants.TILE_SIZE)) {

                    TileType tile = TileType.getIndex((int) tiles[x][y]);
                    if (tile.getImage() != null) {
                        canvas.drawBitmap(tile.getImage(tiles[x][y]), (x * Constants.TILE_SIZE) - camera.getX(), (y * Constants.TILE_SIZE) - camera.getY(), paint);
                    }

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
