package com.mak001.motherload;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.mak001.motherload.game.Constants;
import com.mak001.motherload.game.GamePanel;

public class Motherload extends Activity {

    private MediaPlayer backgroundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Constants.RESOURCES = getResources();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(new GamePanel(this));

        backgroundPlayer = MediaPlayer.create(this, R.raw.loaded_by_tbc_x1x2x3);
        backgroundPlayer.setLooping(true);
        backgroundPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundPlayer.release();
        finish();
    }

}
