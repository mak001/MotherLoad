package com.mak001.motherload.game.player;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.badlogic.gdx.math.Vector2;
import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.helpers.Locatable;
import com.mak001.motherload.game.helpers.Renderable;
import com.mak001.motherload.game.helpers.Updatable;

/**
 * Created by Matthew on 2/21/2017.
 */
public class Player extends Locatable implements Renderable, Updatable {

    private Rect rectangle;
    private int color;


    public Player(Rect rectangle, int color) {
        super();
        this.rectangle = rectangle;
        this.color = color;
        rectangle.offsetTo((Constants.SCREEN_WIDTH / 2) - (rectangle.width() / 2), (Constants.SCREEN_HEIGHT / 2) - (rectangle.height() / 2));
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
        /*
        rectangle.offsetTo(
                (int) (location.x - (rectangle.width() / 2)),
                (int) (location.y - (rectangle.height() / 2))
        );
        */

    }
}
