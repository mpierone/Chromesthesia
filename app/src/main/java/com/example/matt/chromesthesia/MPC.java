package com.example.matt.chromesthesia;

// MPC = MEDIA PLAYER CLASS

import android.media.MediaPlayer;

import java.io.FileNotFoundException;

/**
 * Created by Will Stewart on 9/27/2016.
 */
public class MPC {
    private static MediaPlayer mediaPlayer;

    public MPC() {
        mediaPlayer = new MediaPlayer();
    }

    public void start_pb(String FileName) {
        //mediaPlayer.setDataSource(FileName);

        mediaPlayer.prepareAsync();
        mediaPlayer.start();
        //mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()) {
        // do the thing here thanks
    }


    // }
    public void pause_pb() {
        mediaPlayer.pause();  //should pause

    }
}
