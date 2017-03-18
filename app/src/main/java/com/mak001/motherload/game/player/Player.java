package com.mak001.motherload.game.player;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.Settings;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.Renderable;
import com.mak001.motherload.game.TouchListener;
import com.mak001.motherload.game.Updatable;

/**
 * Created by Matthew on 2/21/2017.
 */
public class Player implements Renderable, Updatable {

    private Vector2 location;
    private Rect rectangle;
    private int color;


    public Player(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
        location = Vector2.Zero.cpy();
        rectangle.offsetTo((int) location.x, (int) location.y);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {
        // TODO - https://www.youtube.com/watch?v=qwuPiaFU37w&t=1250
    }

    public void move(Vector2 vec) {
        location.add(vec.cpy().scl(Constants.MOVE_SPEED));

        rectangle.offsetTo(
                (int) (location.x - (rectangle.width() / 2)),
                (int) (location.y - (rectangle.height() / 2))
        );

    }
}
