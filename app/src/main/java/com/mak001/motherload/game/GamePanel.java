package com.mak001.motherload.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mak001.motherload.game.player.JoyStick;
import com.mak001.motherload.game.player.Player;
import com.mak001.motherload.game.world.World;

/**
 * Created by Matthew on 2/21/2017.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;

    private Player player;
    private Point playerPoint;

    private World world;
    private SoundManager soundManager;
    private JoyStick joyStick;

    private State currentState = State.TITLE_SCREEN;

    public GamePanel(Context context) {
        super(context);
        Constants.GAME_PANEL = this;
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);

        player = new Player(new Rect(0, 0, 96, 96), Color.rgb(255, 255, 0));

        playerPoint = new Point(150, 150);

        world = new World();
        joyStick = new JoyStick(0, 0, 256);

        soundManager = new SoundManager();


        setFocusable(true);

        setState(State.PLAYING);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        switch (currentState) {
            case PLAYING:
                player.onTouchEvent(event);
                joyStick.onTouchEvent(event);
                break;
            case TITLE_SCREEN:

                break;
            case SHOP_OPEN:

                break;
            default:
                // breaks the game, unsure what to do
                return false;
        }

        return true;
    }

    public void update() {
        player.update(playerPoint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.BLACK);

        world.draw(canvas);

        player.draw(canvas);

        if (currentState.equals(State.PLAYING)) {
            joyStick.draw(canvas);
        }
    }

    public void setState(State s) {
        currentState = s;
    }

    public enum State {
        TITLE_SCREEN, PLAYING, SHOP_OPEN
    }
}
