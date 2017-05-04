package com.mak001.motherload.game.world;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.mak001.motherload.game.Camera;
import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.helpers.Renderable;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/25/2017.
 */

public class World implements Renderable {

    private final Tile[][] tiles;
    private int lastGeneratedLayer = 0;

    private final Camera camera;

    public World(Camera camera) {
        this.camera = camera;

        tiles = new Tile[50][500];
        generate(20);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        int camLeft = (int) Math.floor(camera.getX() / Constants.TILE_SIZE) - 1;
        int camRight = camLeft + Constants.TILES_IN_SCREEN_WIDTH + 2;

        int camTop = (int) Math.floor(camera.getY() / Constants.TILE_SIZE) - 1;
        int camBottom = camTop + Constants.TILES_IN_SCREEN_HEIGHT + 2;

        // loops through all tiles on screen
        for (int x = camLeft; x <= camRight; x++) {
            for (int y = camTop; y <= camBottom; y++) {
                // bounds check
                if (x < 0 || y < 0 || tiles.length <= x || tiles[0].length <= y) continue;

                Tile t = tiles[x][y];
                if (t != null) {
                    Rect image = t.getImage();
                    if (image != null) {
                        float adjustedX = t.getX() - camera.getX();
                        float adjustedY = t.getY() - camera.getY();

                        RectF adj = new RectF(adjustedX, adjustedY, adjustedX + Constants.TILE_SIZE, adjustedY + Constants.TILE_SIZE);
                        canvas.drawBitmap(Constants.TILESET, image, adj, paint);
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

    public ArrayList<Tile> getTilesOnX(float x1, float x2, float y) {
        return getTilesBetween(x1, y, x2, y);
    }

    public ArrayList<Tile> getTilesOnY(float x, float y1, float y2) {
        return getTilesBetween(x, y1, x, y2);
    }

    public ArrayList<Tile> getTilesBetween(float x1, float y1, float x2, float y2) {
        return getTilesBetween(x1, y1, x2, y2, false);
    }

    public ArrayList<Tile> getTilesBetween(float x1, float y1, float x2, float y2, boolean includeAir) {
        ArrayList<Tile> tiles = new ArrayList<>();

        // sanity check
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

                // skips null and air if include air is true
                if (t != null && (includeAir || !t.getTileType().equals(TileType.AIR))) {
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

                // TODO
                if ((x == 1 || x == 2) && y == 0) {
                    tiles[x][y].setTileType(TileType.AIR);
                    continue;
                }

                float chance = (float) Math.random();

                for (int i = 0; i < TileType.values().length; i++) {
                    TileType curr = TileType.values()[i];

                    // skips default TitleType (so it does not override previous types)
                    if (curr != TileType.getDefault()) {
                        if (curr.getMinDepth() <= y && y <= curr.getMaxDepth()) {
                            if (chance <= curr.getChance()) {
                                tiles[x][y].setTileType(curr);

                            }
                        } else if (curr.getMinDepth() - 25 <= y && y <= curr.getMaxDepth() + 25) {
                            if (chance <= curr.getChance() / 2) {
                                tiles[x][y].setTileType(curr);
                            }
                        }
                    }
                }
            }
        }

        lastGeneratedLayer += height;
    }

}
