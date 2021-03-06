package com.mak001.motherload.game.player;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.mak001.motherload.R;
import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.Methods;

/**
 * Created by Matthew on 3/18/2017.
 */

public class JoyStick extends Control {


    private final int x;
    private final int y;
    private final int height;
    private final int width;
    private final Point center;
    private final Bitmap image;

    private final Vector2 direction;

    public JoyStick(int x, int y, int size) {
        this(x, y, size, size);
    }

    public JoyStick(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.center = new Point(x + (width / 2), y + (height / 2));

        Bitmap image = BitmapFactory.decodeResource(Constants.RESOURCES, R.drawable.controls_joy_stick);
        this.image = Bitmap.createScaledBitmap(image, width, height, false);

        direction = new Vector2();
    }

    public Vector2 getDirection() {
        return direction;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(image, x, y, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (Methods.between(x, x + width, event.getX()) && Methods.between(y, y + height, event.getY())) {

                    float dx = ((event.getX() - center.x) / width) * 2;
                    float dy = ((event.getY() - center.y) / height) * 2;

                    direction.x = roundTwo(dx);
                    direction.y = roundTwo(dy);
                    // System.out.println(direction);
                } else {
                    direction.x = 0;
                    direction.y = 0;
                }
                break;

            case MotionEvent.ACTION_UP:
                direction.x = 0;
                direction.y = 0;
                break;
        }
        return true;
    }

    private float roundTwo(float f) {
        return Math.round(f * 100f) / 100f;
    }

}
