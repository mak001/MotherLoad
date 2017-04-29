package com.mak001.motherload.game.player;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.badlogic.gdx.math.Vector2;
import com.mak001.motherload.R;
import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.helpers.Locatable;
import com.mak001.motherload.game.helpers.Renderable;
import com.mak001.motherload.game.helpers.Updatable;
import com.mak001.motherload.game.world.Tile;
import com.mak001.motherload.game.world.TileType;

import java.util.ArrayList;


/**
 * Created by Matthew on 2/21/2017.
 */
public class Player extends Locatable implements Renderable, Updatable {

    private final Bitmap image;
    private final RectF display;

    private Rect[][] sprites;
    private long lastFrameMillis;
    private int currentFrame = 0;

    private Vector2 velocity;

    public Player(int x, int y) {
        super(x, y);
        this.velocity = Vector2.Zero();

        lastFrameMillis = System.currentTimeMillis();

        float adjustedX = (Constants.SCREEN_WIDTH / 2f) - (Constants.TILE_SIZE / 2f);
        float adjustedY = (Constants.SCREEN_HEIGHT / 2f) - (Constants.TILE_SIZE / 2f);
        display = new RectF(adjustedX, adjustedY, adjustedX + Constants.TILE_SIZE, adjustedY + Constants.TILE_SIZE);

        // loading image
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        image = BitmapFactory.decodeResource(Constants.RESOURCES, R.drawable.player, options);

        // sprite stuff
        int inX = image.getWidth() / Constants.BITMAP_TILE_SIZE;
        int inY = image.getHeight() / Constants.BITMAP_TILE_SIZE;
        sprites = new Rect[inY][inX];

        for (int i = 0; i < sprites.length; i++) {
            for (int j = 0; j < sprites[0].length; j++) {

                int sx = j * Constants.BITMAP_TILE_SIZE;
                int sy = i * Constants.BITMAP_TILE_SIZE;

                sprites[i][j] = new Rect(sx, sy, sx + Constants.BITMAP_TILE_SIZE, sy + Constants.BITMAP_TILE_SIZE);
                System.out.println(sprites[i][j]);
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(image, sprites[0][currentFrame], display, paint);
    }

    @Override
    public void update(float delta) {

        long curr = System.currentTimeMillis();
        if (Constants.ANIMATION_FRAME_TIME <= curr - lastFrameMillis) {
            currentFrame++;
            lastFrameMillis = curr;
            if (sprites[0].length <= currentFrame) {
                currentFrame = 0;
            }
        }

        // velocity.y -= (Constants.GRAVITY * delta);

        if (Constants.MAX_FALL_SPEED < velocity.y) {
            velocity.y = Constants.MAX_FALL_SPEED;
        } else if (velocity.y < -Constants.MAX_FALL_SPEED) {
            velocity.y = -Constants.MAX_FALL_SPEED;
        }

        Tile collide = null;

        float vx = velocity.x * Constants.MOVE_SPEED_X * delta;
        float vy = velocity.y * Constants.MOVE_SPEED_Y * delta;

        float newX = location.x + vx;
        float newY = location.y + vy;

        ArrayList<Tile> tiles = Constants.WORLD.getTilesBetween(getX(), getY(), newX, newY);

        for (int i = 0; i < tiles.size(); i++) {
            Tile t = tiles.get(i);
            if (t == null || t.getTileType().equals(TileType.AIR)) continue;

            if (collide == null) {
                collide = t;
            } else {
                if (closer(t, collide)) collide = t;
            }
        }

        if (collide != null) {
            // float vx, float vy, float x2, float y2
            float[] collisionTime = sweptAABB(vx, vy, collide.getX(), collide.getY());

            float remainingTime = 1 - collisionTime[0];

            float dotprod = (vx * collisionTime[2] + vy * collisionTime[1]) * remainingTime;
            float newVX = dotprod * collisionTime[2];
            float newVY = dotprod * collisionTime[1];

            location.add((vx * collisionTime[0]) + newVX, (vy * collisionTime[0]) + newVY);
        } else {
            location.add(vx, vy);
        }
    }

    public void move(Vector2 vec, float delta) {
        velocity.x = vec.getX() * (Constants.MOVE_SPEED_X * delta);
        velocity.y = vec.getY() * (Constants.MOVE_SPEED_Y * delta);
    }

    private boolean closer(Locatable a, Locatable b) {
        return closer(this, Constants.PLAYER_SIZE, a, Constants.TILE_SIZE, b, Constants.TILE_SIZE);
    }

    private boolean closer(Locatable loc, int locSize, Locatable a, int sizeA, Locatable b, int sizeB) {
        return (loc.getX() + (locSize / 2)) - (a.getX() + (sizeA / 2)) < (loc.getX() + (locSize / 2)) - (b.getX() + (sizeB / 2)) &&
                (loc.getY() + (locSize / 2)) - (a.getY() + (sizeA / 2)) < (loc.getY() + (locSize / 2)) - (b.getY() + (sizeB / 2));
    }

    private float[] sweptAABB(float vx, float vy, float x2, float y2) {
        return sweptAABB(vx, vy, x2, y2, Constants.TILE_SIZE);
    }

    private float[] sweptAABB(float vx, float vy, float x2, float y2, int size2) {
        return sweptAABB(getX(), getY(), vx, vy, Constants.PLAYER_SIZE, x2, y2, size2);
    }

    private float[] sweptAABB(float x1, float y1, float vx, float vy, int size1, float x2, float y2, int size2) {

        // entry time, normal x, normal y
        float[] returned = new float[]{1f, 0f, 0f};

        float xInvEntry, yInvEntry;
        float xEntry, yEntry;

        if (vx > 0f) {
            xInvEntry = x2 - (x1 + size1);
        } else {
            xInvEntry = (x2 + size2) - x1;
        }

        if (vy > 0f) {
            yInvEntry = y2 - (y1 + size1);
        } else {
            yInvEntry = (y2 + size2) - y1;
        }

        if (vx == 0f) {
            xEntry = Float.MIN_VALUE;
        } else {
            xEntry = (xInvEntry / size2) / vx;
        }

        if (vy == 0f) {
            yEntry = Float.MIN_VALUE;
        } else {
            yEntry = (yInvEntry / size2) / vy;
        }

        float entryTime = Math.abs(Math.max(xEntry, yEntry));
        float exitTime = Math.abs(Math.min(xEntry, yEntry));

        if (entryTime > exitTime || xEntry < 0f && yEntry < 0f || xEntry > 1f || yEntry > 1f) {
            return returned;

        } else {// if there was a collision
            // calculate normal of collided surface
            if (xEntry > yEntry) {
                if (xInvEntry < 0f) {
                    returned[1] = 1f;
                    returned[2] = 0f;
                } else {
                    returned[1] = -1f;
                    returned[2] = 0f;
                }
            } else {
                if (yInvEntry < 0f) {
                    returned[1] = 0f;
                    returned[2] = 1f;
                } else {
                    returned[1] = 0f;
                    returned[2] = -1f;
                }
            }
            returned[0] = (entryTime * size2);
            return returned;
        }
    }

}
