package com.mak001.motherload.game.player;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.badlogic.gdx.math.Vector2;
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

    private int color;
    private Rect image;
    private Vector2 velocity;


    public Player(Rect rectangle, int color) {
        super(rectangle.left, rectangle.top);
        this.color = color;
        this.velocity = Vector2.Zero();

        image = rectangle;
        setPos((Constants.SCREEN_WIDTH / 2) - (rectangle.width() / 2), (Constants.SCREEN_HEIGHT / 2) - (rectangle.height() / 2));
        image.offsetTo((Constants.SCREEN_WIDTH / 2) - (image.width() / 2), (Constants.SCREEN_HEIGHT / 2) - (image.height() / 2));

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        // canvas.drawRect(collider, paint);

        // paint.setColor(Color.CYAN);
        canvas.drawRect(image, paint);
    }

    @Override
    public void update(float delta) {
        // TODO - https://www.youtube.com/watch?v=qwuPiaFU37w&t=1250
        Vector2 oldLoc = getLocation();
        boolean collideX = false;
        boolean collideY = false;

        // velocity.y -= (Constants.GRAVITY.getY() * Constants.MOVE_SPEED * delta);


        if (Constants.MAX_FALL_SPEED < velocity.y) {
            velocity.y = Constants.MAX_FALL_SPEED;
        } else if (velocity.y < -Constants.MAX_FALL_SPEED) {
            velocity.y = -Constants.MAX_FALL_SPEED;
        }

        location.x = location.x + velocity.x * Constants.MOVE_SPEED * delta;
        location.y = location.y + velocity.y * Constants.MOVE_SPEED * delta;

        ArrayList<Tile> tiles = Constants.WORLD.getTilesAround(location, 1);

        int sX = -1;
        for (Tile t : tiles) {
            sX = getColidingSide(t);
            if (sX == 1 || sX == 3) {
                collideX = true;
            }
        }

        if (collideX) {
            location.x = oldLoc.getX();
            velocity.x = 0;
        }

        int sY = -1;
        for (Tile t : tiles) {
            System.out.println(isColliding(t) + " :: " + t);
            sY = getColidingSide(t);
            if (sY == 1 || sY == 3) {
                collideY = true;
            }
        }

        if (collideY) {
            location.y = oldLoc.getY();
            velocity.y = 0;
        }


        System.out.println(location + " :: " + sX + ", " + sY);

    }

    private boolean isCollision(float x, float y) {
        return Constants.WORLD.getTileAt((int) (x / Constants.TILE_SIZE), (int) (y / Constants.TILE_SIZE)).getTileType().ordinal() != 0;
    }

    public void move(Vector2 vec, float delta) {

        velocity.add(vec.cpy().scl(Constants.MOVE_SPEED * delta));

        /*
        rectangle.offsetTo(
                (int) (location.x - (rectangle.width() / 2)),
                (int) (location.y - (rectangle.height() / 2))
        );
        */

    }
}
