package com.mak001.motherload;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.GamePanel;

public class Motherload extends Activity {

    private MediaPlayer backgroundPlayer;
    private GamePanel panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Constants.RESOURCES = getResources();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Constants.TILESET = BitmapFactory.decodeResource(getResources(), R.drawable.tiles, options);

        panel = new GamePanel(this);
        setContentView(panel);
    }

    @Override
    protected void onPause() {
        backgroundPlayer.pause();
        // panel.pause();

        super.onPause();
    }

    @Override
    protected void onResume() {
        backgroundPlayer.start();
        //panel.resume();

        super.onResume();
    }

    @Override
    protected void onStop() {
        backgroundPlayer.stop();
        backgroundPlayer.release();
        // panel.pause();

        super.onStop();
    }

    @Override
    protected void onStart() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        createMediaPlayer();
        backgroundPlayer.start();

        super.onStart();
    }

    private void createMediaPlayer() {
        backgroundPlayer = MediaPlayer.create(this, R.raw.loaded_by_tbc_x1x2x3);
        backgroundPlayer.setLooping(true);
    }

}
