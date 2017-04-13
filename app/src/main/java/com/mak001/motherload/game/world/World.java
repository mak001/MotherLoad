package com.mak001.motherload.game.world;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.badlogic.gdx.math.Vector2;
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

    public ArrayList<Tile> getTilesAround(Vector2 vec, int range) {
        ArrayList<Tile> tiles = new ArrayList<Tile>();

        int realX = Math.round(vec.getX() / Constants.TILE_SIZE);
        int realY = Math.round(vec.getY() / Constants.TILE_SIZE);

        for (int i = realX - range; i <= realX + range; i++) {
            for (int j = realY - range; j <= realY + range; j++) {
                Tile t = getTileAt(i, j);
                if (t != null)
                    tiles.add(t);
            }
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
