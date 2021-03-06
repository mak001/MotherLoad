package com.mak001.motherload.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    private JoyStick joyStick;
    private Player player;
    private Camera camera;

    private SoundManager soundManager;


    private State currentState = State.TITLE_SCREEN;

    public GamePanel(Context context) {
        super(context);

        Constants.SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
        Constants.SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

        Constants.GAME_PANEL = this;
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);

        camera = new Camera();

        player = new Player(Constants.PLAYER_SIZE, -(Constants.PLAYER_SIZE + 1));

        Constants.WORLD = new World(camera);
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

    public void update(float delta) {
        player.move(joyStick.getDirection(), delta);
        player.update(delta);
        camera.setPos((player.getX() + Constants.PLAYER_SIZE / 2) - (Constants.SCREEN_WIDTH / 2), (player.getY() + Constants.PLAYER_SIZE / 2) - (Constants.SCREEN_HEIGHT / 2));

        //System.out.println(player.getLocation() + " :: (" + (camera.getX() + (Constants.SCREEN_WIDTH / 2)) + ", " + (camera.getY() + Constants.SCREEN_HEIGHT / 2) + ")");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Paint paint = new Paint();

        canvas.drawColor(Constants.SKY_COLOR);

        Constants.WORLD.draw(canvas, paint);
        player.draw(canvas, paint);

        if (currentState.equals(State.PLAYING)) {
            joyStick.draw(canvas, paint);
        }
    }

    public void setState(State s) {
        currentState = s;
    }

    private enum State {
        TITLE_SCREEN, PLAYING, SHOP_OPEN
    }
}
