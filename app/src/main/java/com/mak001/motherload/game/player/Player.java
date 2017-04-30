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

import java.util.ArrayList;


/**
 * Created by Matthew on 2/21/2017.
 */
public class Player extends Locatable implements Renderable, Updatable {

    private final Bitmap image;
    private final RectF display;

    private final Rect[][] sprites;
    private long lastFrameMillis;
    private int currentFrame = 0;

    private final Vector2 velocity;

    public Player(int x, int y) {
        super(x, y);
        this.velocity = new Vector2();

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

    public void move(Vector2 vec, float delta) {
        velocity.x = vec.getX() * (Constants.MOVE_SPEED_X * delta);

        // TODO - keep positive y velocity (for gravity)
        velocity.y = vec.getY() * (Constants.MOVE_SPEED_Y * delta);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(image, sprites[0][currentFrame], display, paint);
    }

    @Override
    public void update(float delta) {

        // frames
        long curr = System.currentTimeMillis();
        if (Constants.ANIMATION_FRAME_TIME <= curr - lastFrameMillis) {
            currentFrame++;
            lastFrameMillis = curr;
            if (sprites[0].length <= currentFrame) {
                currentFrame = 0;
            }
        }

        // gravity
        velocity.y += (Constants.GRAVITY * delta);
        // clamp gravity force (terminal velocity)
        if (Constants.MAX_FALL_SPEED < velocity.y) {
            velocity.y = Constants.MAX_FALL_SPEED;
        }
        // System.out.println("G = " + Constants.MAX_FALL_SPEED + " :: V.y = " + velocity.getY());

        float vx = velocity.x * Constants.MOVE_SPEED_X * delta;
        float vy = velocity.y * Constants.MOVE_SPEED_Y * delta;

        float newX = location.x + vx;
        float newY = location.y + vy;

        ArrayList<Tile> tiles = Constants.WORLD.getTilesBetween(getX(), getY(), newX, newY);

        if (tiles.size() == 0) {
            location.add(vx, vy);

        } else {
            float[][] times = getTimes(vx, vy, tiles);
            int closestTime = getNearest(times);

            float remainingTime = 1 - times[closestTime][0];
            float dotprod = (vx * times[closestTime][2] + vy * times[closestTime][1]) * remainingTime;
            float newVX = dotprod * times[closestTime][2];
            float newVY = dotprod * times[closestTime][1];

            float nX = vx * times[closestTime][0] * times[closestTime][0];
            float nY = vy * times[closestTime][0] * times[closestTime][0];

            // TODO - clamp if second collision
            location.add(nX + newVX, nY + newVY);
        }
    }

    private float[][] getTimes(float vx, float vy, ArrayList<Tile> tiles) {
        float[][] times = new float[tiles.size()][3];

        for (int i = 0; i < tiles.size(); i++) {
            Tile t = tiles.get(i);
            times[i] = sweptAABB(vx, vy, t.getX(), t.getY());
        }
        return times;
    }

    private int getNearest(float[][] times) {
        int closestTime = 0;
        if (1 < times.length) {
            for (int i = 1; i < times.length; i++) {
                if (times[i][0] < times[closestTime][0]) closestTime = i;
            }
        }
        return closestTime;
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

        float xInvEntry, xInvExit, yInvEntry, yInvExit;
        // find the distance between the objects on the near and far sides for both x and y
        if (vx > 0.0f) {
            xInvEntry = x2 - (x1 + size1);
            xInvExit = (x2 + size2) - x1;
        } else {
            xInvEntry = (x2 + size2) - x1;
            xInvExit = x2 - (x1 + size1);
        }

        if (vy > 0.0f) {
            yInvEntry = y2 - (y1 + size1);
            yInvExit = (y2 + size2) - y1;
        } else {
            yInvEntry = (y2 + size2) - y1;
            yInvExit = y2 - (y1 + size1);
        }


        float xEntry, xExit, yEntry, yExit;
        // find time of collision and time of leaving for each axis (if statement is to prevent divide by zero)
        if (vx == 0.0f) {
            xEntry = -Float.MAX_VALUE;
            xExit = Float.MAX_VALUE;
        } else {
            xEntry = xInvEntry / vx;
            xExit = xInvExit / vx;
        }

        if (vy == 0.0f) {
            yEntry = -Float.MAX_VALUE;
            yExit = Float.MAX_VALUE;
        } else {
            yEntry = yInvEntry / vy;
            yExit = yInvExit / vy;
        }

        float entryTime = Math.max(xEntry, yEntry);
        float exitTime = Math.min(xExit, yExit);

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
            returned[0] = entryTime;
            return returned;
        }
    }

}
