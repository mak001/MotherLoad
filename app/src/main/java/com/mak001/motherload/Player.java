package com.mak001.motherload;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Matthew on 2/21/2017.
 */
public class Player implements GameObject {

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

    }

    public void update(Point point) {
        rectangle.set(point.x - (rectangle.width() / 2), (point.y - rectangle.height() / 2), point.x + (rectangle.width() / 2), (point.y + rectangle.height() / 2));
    }
}
