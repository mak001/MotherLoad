package com.mak001.motherload.game.player;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

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

    private Bitmap image;
    private Vector2 velocity;

    public Player(int x, int y) {
        super(x, y);
        this.velocity = Vector2.Zero();

        image = BitmapFactory.decodeResource(Constants.RESOURCES, R.drawable.player);
        image = Bitmap.createScaledBitmap(image, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE, false);


    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(image, (Constants.SCREEN_WIDTH / 2) - (Constants.PLAYER_SIZE / 2), (Constants.SCREEN_HEIGHT / 2) - (Constants.PLAYER_SIZE / 2), paint);
    }

    @Override
    public void update(float delta) {

        // velocity.y -= (Constants.GRAVITY * delta);

        if (Constants.MAX_FALL_SPEED < velocity.y) {
            velocity.y = Constants.MAX_FALL_SPEED;
        } else if (velocity.y < -Constants.MAX_FALL_SPEED) {
            velocity.y = -Constants.MAX_FALL_SPEED;
        }

        // Vector2 oldLoc = getLocation().cpy();
        Tile collideX = null;
        Tile collideY = null;

        // velocity.y -= (Constants.GRAVITY * delta);

        if (Constants.MAX_FALL_SPEED < velocity.y) {
            velocity.y = Constants.MAX_FALL_SPEED;
        } else if (velocity.y < -Constants.MAX_FALL_SPEED) {
            velocity.y = -Constants.MAX_FALL_SPEED;
        }

        // setX(location.x + velocity.x * Constants.MOVE_SPEED_X * delta);
        // setY(location.y + velocity.y * Constants.MOVE_SPEED_Y * delta);

        float newX = location.x + velocity.x * Constants.MOVE_SPEED_X * delta;
        float newY = location.y + velocity.y * Constants.MOVE_SPEED_Y * delta;

        ArrayList<Tile> tiles = Constants.WORLD.getTilesBetween(getX(), getY(), newX, newY);

        for (int i = 0; i < tiles.size(); i++) {
            Tile t = tiles.get(i);
            if (t.getTileType().equals(TileType.AIR)) continue;
            if (collides(t, Constants.TILE_SIZE)) {
                if (collideY == null) {
                    collideY = t;
                } else {
                    if (closer(t, collideY)) collideY = t;
                }
            }
        }

        if (collideY != null) {
            float collisiontime = sweptAABB(collideY.getX(), collideY.getY());
            System.out.println(collisiontime);
            location.add((velocity.x * Constants.MOVE_SPEED_X * delta) * collisiontime, (velocity.y * Constants.MOVE_SPEED_Y * delta) * collisiontime);
        } else {
            setPos(newX, newY);
        }

        /*
        if (collideY != null) {
            if (getY() < newY) { // moving down
                setY(collideY.getY() + Constants.TILE_SIZE);
                System.out.println("Moving down");

            } else { // moving up
                setY(collideY.getY() - Constants.PLAYER_SIZE);
                System.out.println("Moving up");
            }
            velocity.y = 0;
        } else {
            setY(newY);
        }

        // setY(newY);

        for (int i = 0; i < tiles.size(); i++) {
            Tile t = tiles.get(i);
            if (t.getTileType().equals(TileType.AIR)) continue;
            if (collides(t, Constants.TILE_SIZE)) {
                if (collideX == null) {
                    collideX = t;
                } else {
                    if (closer(t, collideX)) collideX = t;
                }
            }
        }

        if (collideX != null) {
            if (getX() < newX) { // moving right
                setX(collideX.getX() - Constants.PLAYER_SIZE);
                System.out.println("Moving right");

            } else { // moving left
                setX(collideX.getX() + Constants.TILE_SIZE);
                System.out.println("Moving left");

            }
            velocity.x = 0;
        } else {
            setX(newX);
        }
        */
        // setX(newX);

    }

    public void move(Vector2 vec, float delta) {
        velocity.x = vec.getX() * (Constants.MOVE_SPEED_X * delta);
        velocity.y = vec.getY() * (Constants.MOVE_SPEED_Y * delta);
    }

    private boolean collidesX(Locatable loc, int size, float x) {
        return x < loc.getX() + size && x + Constants.PLAYER_SIZE > loc.getX();
    }

    private boolean collidesY(Locatable loc, int size, float y) {
        return y < loc.getY() + size && y + Constants.PLAYER_SIZE > loc.getY();
    }

    private boolean collides(Locatable loc, int size) {
        return collidesX(loc, size, getX()) && collidesY(loc, size, getY());
    }

    private boolean closer(Locatable a, Locatable b) {
        return closer(this, a, b);
    }

    private boolean closer(Locatable loc, Locatable a, Locatable b) {
        return loc.getX() - a.getX() < loc.getX() - b.getX() && loc.getY() - a.getY() < loc.getY() - b.getY();
    }

    private float sweptAABB(float x, float y) {
        return sweptAABB(x, y, Constants.TILE_SIZE);
    }

    private float sweptAABB(float x, float y, int size) {
        return sweptAABB(getX(), getY(), Constants.PLAYER_SIZE, x, y, size);
    }

    private float sweptAABB(float x1, float y1, int size1, float x2, float y2, int size2) {
        // float normalx;
        // loat normaly;

        float xInvEntry, yInvEntry;
        float xEntry, yEntry;

        if (velocity.getX() > 0.0f) {
            xInvEntry = x2 - (x1 + size1);
        } else {
            xInvEntry = (x2 + size2) - x1;
        }

        if (velocity.getY() > 0.0f) {
            yInvEntry = y2 - (y1 + size1);
        } else {
            yInvEntry = (y2 + size2) - y1;
        }


        if (velocity.getX() == 0.0f) {
            xEntry = Float.MIN_VALUE;
        } else {
            xEntry = xInvEntry / velocity.getX();
        }

        if (velocity.getY() == 0.0f) {
            yEntry = Float.MIN_VALUE;
        } else {
            yEntry = yInvEntry / velocity.getY();
        }

        System.out.println("(" + xInvEntry + ", " + yInvEntry + ") :: (" + xEntry + ", " + yEntry + ") :: (" + velocity.getX() + ", " + velocity.getY() + ")");

        float entryTime = xEntry < 0 && yEntry < 0 ? Math.min(xEntry, yEntry) : Math.max(xEntry, yEntry);
        float exitTime = xEntry < 0 && yEntry < 0 ? Math.max(xEntry, yEntry) : Math.min(xEntry, yEntry);

        if (entryTime > exitTime || xEntry < 0.0f && yEntry < 0.0f || xEntry > 1.0f || yEntry > 1.0f) {
            // normalx = 0.0f;
            // normaly = 0.0f;
            System.out.println(entryTime + " :: " + exitTime);
            System.out.println("no collision");
            return 1.0f;
        } else {// if there was a collision
            // calculate normal of collided surface
            /*
            if (xEntry > yEntry) {
                if (xInvEntry < 0.0f) {
                    // normalx = 1.0f;
                    // normaly = 0.0f;
                } else {
                    // normalx = -1.0f;
                    // normaly = 0.0f;
                }
            } else {
                if (yInvEntry < 0.0f) {
                    // normalx = 0.0f;
                    // normaly = 1.0f;
                } else {
                    // normalx = 0.0f;
                    // normaly = -1.0f;
                }
            }*/
            return entryTime;
        }
    }

}
