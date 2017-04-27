package com.mak001.motherload.game.world;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.mak001.motherload.game.Camera;
import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.Methods;
import com.mak001.motherload.game.helpers.Renderable;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/25/2017.
 */

public class World implements Renderable {

    private Tile[][] tiles;
    private int lastGeneratedLayer = 0;

    private final Camera camera;

    public World(Camera camera) {
        this.camera = camera;

        tiles = new Tile[50][500];
        generate(20);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        // TODO narrow loops
        for (Tile[] tile : tiles) {
            for (int y = 0; y < Math.min(lastGeneratedLayer, tiles[0].length); y++) {
                if (tile[y] != null) {
                    Tile t = tile[y];

                    if (Methods.between(Math.floor(camera.getX() - Constants.TILE_SIZE), Math.ceil(camera.getX() + Constants.SCREEN_WIDTH + Constants.TILE_SIZE), t.getX()) &&
                            Methods.between(Math.floor(camera.getY() - Constants.TILE_SIZE), Math.ceil(camera.getY() + Constants.SCREEN_HEIGHT + Constants.TILE_SIZE), t.getY())) {

                        Bitmap image = t.getImage();
                        if (image != null) {
                            canvas.drawBitmap(image, t.getX() - camera.getX(), t.getY() - camera.getY(), paint);
                        }
                    }
                }

            }
        }
    }

    private Tile getTileAt(int x, int y) {
        if (0 <= x && x < tiles.length && 0 <= y && y < tiles[0].length) {
            return tiles[x][y];
        }
        return null;
    }

    public ArrayList<Tile> getTilesBetween(float x1, float y1, float x2, float y2) {
        ArrayList<Tile> tiles = new ArrayList<>();

        // probably never gonna happen (gravity), but a good sanity check
        if (x1 == x2 && y1 == y2) {
            return tiles;
        }

        float minX = Math.min(x1, x2);
        float maxX = Math.max(x1, x2);
        float minY = Math.min(y1, y2);
        float maxY = Math.max(y1, y2);

        int snappedX1 = (int) Math.floor(minX / Constants.TILE_SIZE);
        int snappedX2 = (int) Math.ceil(maxX / Constants.TILE_SIZE);
        int snappedY1 = (int) Math.floor(minY / Constants.TILE_SIZE);
        int snappedY2 = (int) Math.ceil(maxY / Constants.TILE_SIZE);

        int tilesBetweenX = snappedX2 - snappedX1;
        int tilesBetweenY = snappedY2 - snappedY1;

        for (int i = 0; i <= tilesBetweenX; i++) {
            for (int j = 0; j <= tilesBetweenY; j++) {
                Tile t = getTileAt(snappedX1 + i, snappedY1 + j);

                if (t != null) {
                    tiles.add(t);
                }
            }
        }

        return tiles;
    }



    public void generate(int height) {
        // TODO - generate TileTypes within their given range
        for (int x = 0; x < tiles.length; x++) {
            for (int y = lastGeneratedLayer; y < Math.min(lastGeneratedLayer + height, tiles[0].length); y++) {

                // set the default TileType
                tiles[x][y] = new Tile(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, TileType.getDefault());

                float chance = (float) Math.random();

                for (int i = 0; i < TileType.values().length; i++) {
                    TileType curr = TileType.getIndex(i);

                    // skips default TitleType (so it does not override previous types)
                    if (curr != TileType.getDefault()) {
                        if (chance <= curr.getChance()) {
                            tiles[x][y].setTileType(curr);

                        }
                    }
                }
            }
        }

        lastGeneratedLayer += height;
    }
}
