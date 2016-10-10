package com.example.matt.chromesthesia;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import android.media.MediaPlayer;
import android.widget.ToggleButton;

import com.example.matt.chromesthesia.MPC;

/**
 * Created by Matt on 10/8/2016.
 */

public class NowPlayingScreen extends Chromesthesia {

    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.playscreen);

        final ToggleButton playButton = (ToggleButton) findViewById(R.id.playButton);

        //final MPC media = new MPC();
        final MediaPlayer media = MediaPlayer.create(this, R.raw.sutphinboulevard);     //probably will be different when media reading is finished

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
