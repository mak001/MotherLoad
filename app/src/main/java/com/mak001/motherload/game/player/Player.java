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
import com.mak001.motherload.game.world.World;

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

        // setX(location.x + velocity.x * Constants.MOVE_SPEED_X * delta);
        // setY(location.y + velocity.y * Constants.MOVE_SPEED_Y * delta);
        float newX = location.getX() + velocity.getX() * Constants.MOVE_SPEED_X * delta;
        float newY = location.getY() + velocity.getY() * Constants.MOVE_SPEED_Y * delta;

        ArrayList<Tile> tiles = Constants.WORLD.getTilesAround(newX, newY, 1);

        for (int i = 0; i < tiles.size(); i++) {
            Tile t = tiles.get(i);

            if (!t.getTileType().equals(TileType.AIR)) {
                if (collidesX(t, Constants.TILE_SIZE, newX) && collidesY(t, Constants.TILE_SIZE, newY)) {

                    if (collidesY(t, Constants.TILE_SIZE, newY)) {
                        if (newY < getY()) { // going up
                            System.out.println("Collision going up");
                            newY = (t.getY() + Constants.TILE_SIZE) + 1;
                        } else if (getY() < newY) { // going down
                            System.out.println("Collision going down");
                            newY = (t.getY() - Constants.PLAYER_SIZE) - 1;
                        }
                    }

                    if (collidesX(t, Constants.TILE_SIZE, newX)) {
                        if (getX() < newX) { // going right
                            System.out.println("Collision going right");
                            newX = (t.getX() - Constants.PLAYER_SIZE) - 1;
                        } else if (newX < getX()) { // going left
                            System.out.println("Collision going left");
                            newX = (t.getX() + Constants.TILE_SIZE) + 1;
                        }
                    }
                }
            }
        }

        setPos(newX, newY);
    }

    public void move(Vector2 vec, float delta) {
        velocity.x = vec.getX() * (Constants.MOVE_SPEED_X * delta);
        velocity.y = vec.getY() * (Constants.MOVE_SPEED_Y * delta);
        // velocity.set(vec.cpy().scl(Constants.MOVE_SPEED * delta));
    }

    private boolean collidesX(Locatable loc, int size, float x) {
        return x < loc.getX() + size && x + Constants.PLAYER_SIZE > loc.getX();
    }

    private boolean collidesY(Locatable loc, int size, float y) {
        return y < loc.getY() + size && y + Constants.PLAYER_SIZE > loc.getY();
    }

    private boolean collidesX(Locatable loc, int size) {
        return collidesX(loc, size, getX());
    }

    private boolean collidesY(Locatable loc, int size) {
        return collidesY(loc, size, getY());
    }

}
