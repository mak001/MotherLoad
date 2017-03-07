package com.mak001.motherload.game;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by Matthew on 2/27/2017.
 */

public class SoundManager {

    private static SoundPool soundPool;

    @SuppressWarnings("deprecation")
    public SoundManager() {
        if (Build.VERSION.SDK_INT > 20) {
            AudioAttributes atts = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED).build();
            soundPool = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(atts).build();

        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
    }

    public void setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener onLoadCompleteListener) {
        soundPool.setOnLoadCompleteListener(onLoadCompleteListener);
    }

}
