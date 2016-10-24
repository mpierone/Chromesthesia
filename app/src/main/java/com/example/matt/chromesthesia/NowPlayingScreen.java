package com.example.matt.chromesthesia;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

/**
 * Created by Matt on 10/8/2016.
 */

public class NowPlayingScreen extends Chromesthesia {


    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.playscreen);

        final ToggleButton playButton = (ToggleButton) findViewById(R.id.playButton);
        final ImageButton previousButton = (ImageButton) findViewById(R.id.previousButton);
        final ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);

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

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call and play previous song
                //if song[index-1] would == null, then go to song[highest_index]
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call and play next song
                //if song[index+1] is out of bounds, then go to song[index_1]
            }
        });

    }
}