package com.mak001.motherload.game.world;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.mak001.motherload.game.Camera;
import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.Methods;
import com.mak001.motherload.game.helpers.Renderable;

/**
 * Created by Matthew on 2/25/2017.
 */

public class World implements Renderable {

    private Tile[][] tiles;
    private int lastGeneratedLayer = 0;

    private final Camera camera;

    public World(Camera camera) {
        this.camera = camera;

        tiles = new Tile[50][25];
        generate(tiles.length);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < getLower(lastGeneratedLayer, tiles[0].length); y++) {

                if (Methods.between(Math.floor(camera.getX() - Constants.TILE_SIZE), Math.ceil(camera.getX() + Constants.SCREEN_WIDTH + Constants.TILE_SIZE), x * Constants.TILE_SIZE) &&
                        Methods.between(Math.floor(camera.getY() - Constants.TILE_SIZE), Math.ceil(camera.getY() + Constants.SCREEN_HEIGHT + Constants.TILE_SIZE), y * Constants.TILE_SIZE)) {

                    if (tiles[x][y] != null) {
                        Tile t = tiles[x][y];
                        Bitmap image = t.getImage();
                        if (image != null) {
                            canvas.drawBitmap(image, t.getX() - camera.getX(), t.getY() - camera.getY(), paint);
                        }

                        if (tiles[x][y].canCollide()) {
                            paint.setStyle(Paint.Style.STROKE);
                            paint.setColor(Color.RED);
                            canvas.drawRect(tiles[x][y].getCollider(), paint);
                            // System.out.println("Drew tiles at: " + x + ", " + y + " :: " + tiles[x][y].getCollider());
                        }
                    }
                }

            }
        }
    }

    public Tile getTileAt(int x, int y) {
        if (0 < x && x < tiles.length && 0 < y && y < tiles[0].length) {
            return tiles[x][y];
        }
        return null;
    }

    public Tile[][] getTilesAround(int x, int y, int range) {

        x /= Constants.TILE_SIZE;
        y /= Constants.TILE_SIZE;

        Tile[][] tiles = new Tile[range + 30][range + 30];
        int halfRange = range / 2;
        int sX = 0;
        int sY = 0;

        for (int i = x - halfRange; i < x + halfRange; i++) {
            for (int j = y - halfRange; j < y + halfRange; j++) {
                if (i != x && j != y) { //ignore the center tile
                    tiles[sX][sY] = getTileAt(i, j);
                } else {
                    tiles[sX][sY] = null;
                }
                sY++;
            }
            sX++;
        }

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
