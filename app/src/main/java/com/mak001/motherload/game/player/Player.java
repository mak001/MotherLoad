package com.mak001.motherload.game.player;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.badlogic.gdx.math.Vector2;
import com.mak001.motherload.R;
import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.helpers.Collidable;
import com.mak001.motherload.game.helpers.Renderable;
import com.mak001.motherload.game.helpers.Updatable;

import com.mak001.motherload.game.world.Tile;

import java.util.ArrayList;


/**
 * Created by Matthew on 2/21/2017.
 */
public class Player extends Collidable implements Renderable, Updatable {

    private Bitmap image;
    private Vector2 velocity;

    public Player(int x, int y) {
        super(x, y, Constants.PLAYER_SIZE);
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
        // TODO - https://www.youtube.com/watch?v=qwuPiaFU37w&t=1250
        Vector2 oldLoc = getLocation().cpy();
        Tile collideX = null;
        Tile collideY = null;

        velocity.y -= (Constants.GRAVITY * delta);

        if (Constants.MAX_FALL_SPEED < velocity.y) {
            velocity.y = Constants.MAX_FALL_SPEED;
        } else if (velocity.y < -Constants.MAX_FALL_SPEED) {
            velocity.y = -Constants.MAX_FALL_SPEED;
        }

        setX(location.x + velocity.x * Constants.MOVE_SPEED_X * delta);
        setY(location.y + velocity.y * Constants.MOVE_SPEED_Y * delta);

        ArrayList<Tile> tiles = Constants.WORLD.getTilesAround(location, 1);

        for (int i = 0; i < tiles.size(); i++) {
            int sY = getColidingSide(tiles.get(i), 1);
            if (sY == 0 || sY == 2) {
                collideY = tiles.get(i);
                break;
            }
        }

        if (collideY != null) {
            // todo - move to collision

            if (oldLoc.getY() < location.getY()) { // moving up
                setY(collideY.getY() - Constants.PLAYER_SIZE);
            } else if (location.getY() < oldLoc.getY()) { // moving down
                setY(collideY.getY() + Constants.TILE_SIZE);
            }


            velocity.y = 0;
        }

        for (int i = 0; i < tiles.size(); i++) {
            int sX = getColidingSide(tiles.get(i), 0);
            if (sX == 1 || sX == 3) {
                collideX = tiles.get(i);
                break;
            }
        }

        if (collideX != null) {
            // todo - move to collision
            if (oldLoc.getX() < location.getX()) { // moving right
                setX(collideX.getX() - Constants.PLAYER_SIZE);
            } else if (location.getX() < oldLoc.getX()) { // moving left
                setX(collideX.getX() + Constants.TILE_SIZE);
            }
            velocity.x = 0;
        }

    }

    public void move(Vector2 vec, float delta) {
        velocity.x = vec.getX() * (Constants.MOVE_SPEED_X * delta);
        velocity.y = vec.getY() * (Constants.MOVE_SPEED_Y * delta);
        // velocity.set(vec.cpy().scl(Constants.MOVE_SPEED * delta));
    }
}
