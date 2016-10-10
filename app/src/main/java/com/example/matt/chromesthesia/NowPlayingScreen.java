package com.example.matt.chromesthesia;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Created by Matt on 10/8/2016.
 */

public class NowPlayingScreen extends Chromesthesia {

    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.playscreen);

        final ToggleButton playButton = (ToggleButton) findViewById(R.id.playButton);

        /*final MPC media = new MPC();
        iacaneda: If MediaPlayer.create needs a File to play, then we want our Library's
        list options to change this MediaPlayer file.
        */
        final MediaPlayer media = MediaPlayer.create(this, R.raw.sutphinboulevard);


        playButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //media.start_pb(file);
                if (isChecked) {
                    media.start();
                } else {
                    media.pause();
                }
            }
        });


    }
}
