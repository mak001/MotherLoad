package com.mak001.motherload.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.mak001.motherload.R;

/**
 * Created by Matthew on 2/27/2017.
 */

public class SoundManager {

    private static SoundPool soundPool;

    public SoundManager() {

        AudioAttributes atts = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED).build();
        soundPool = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(atts).build();

    }

    public void setOnLoadCompleteListener(SoundPool.OnLoadCompleteListener onLoadCompleteListener) {
        soundPool.setOnLoadCompleteListener(onLoadCompleteListener);
    }

}
