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

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < getLower(lastGeneratedLayer, tiles[0].length); y++) {
                if (tiles[x][y] != null) {
                    Tile t = tiles[x][y];

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

    public Tile getTileAt(int x, int y) {
        if (0 <= x && x < tiles.length && 0 <= y && y < tiles[0].length) {
            return tiles[x][y];
        }
        return null;
    }

    public ArrayList<Tile> getTilesAround(float x, float y, int range) {
        ArrayList<Tile> tiles = new ArrayList<Tile>();

        int realX = Math.round(x / Constants.TILE_SIZE);
        int realY = Math.round(y / Constants.TILE_SIZE);

        for (int i = realX - range; i <= realX + range; i++) {
            for (int j = realY - range; j <= realY + range; j++) {
                Tile t = getTileAt(i, j);
                if (t != null)
                    tiles.add(t);
            }
        }
        return tiles;
    }

    public ArrayList<Tile> getTilesBetween(float x1, float x2, float y1, float y2) {
        ArrayList<Tile> tiles = new ArrayList<Tile>();

        if (x1 == x2 && y1 == y2) {
            return tiles;
        }

        float minX = Math.min(x1, x2);
        float maxX = Math.max(x1, x2);
        float minY = Math.min(y1, y2);
        float maxY = Math.max(y1, y2);

        int snappedX1 = (int) (minX / Constants.TILE_SIZE);
        int snappedX2 = (int) (maxX / Constants.TILE_SIZE);
        int snappedY1 = (int) (minY / Constants.TILE_SIZE);
        int snappedY2 = (int) (minY / Constants.TILE_SIZE);


        int tilesBetweenX = snappedX1 - snappedX2;
        int tilesBetweenY = snappedY1 - snappedY2;

        return tiles;
    }



    public void generate(int height) {
        // TODO - generate TileTypes within their given range
        for (int x = 0; x < tiles.length; x++) {
            for (int y = lastGeneratedLayer; y < getLower(lastGeneratedLayer + height, tiles[0].length); y++) {

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

    public int getLower(int a, int b) {
        if (a <= b) {
            return a;
        }
        return b;
    }
}
