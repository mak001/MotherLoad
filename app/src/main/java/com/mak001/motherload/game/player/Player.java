package com.mak001.motherload.game.player;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.mak001.motherload.game.Renderable;
import com.mak001.motherload.game.TouchListener;
import com.mak001.motherload.game.Updatable;

/**
 * Created by Matthew on 2/21/2017.
 */
public class Player implements Renderable, Updatable, TouchListener {

    private Rect rectangle;
    private int color;


    public Player(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
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

    public void update(Point point) {
        rectangle.set(point.x - (rectangle.width() / 2), (point.y - rectangle.height() / 2), point.x + (rectangle.width() / 2), (point.y + rectangle.height() / 2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO
        return true;
    }
}
