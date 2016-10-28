package com.example.matt.chromesthesia;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
        final ImageButton previousButton = (ImageButton) findViewById(R.id.previousButton);
        final ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
        TextView songTitle = (TextView) findViewById(R.id.songTitleText);
        final RadioGroup repeatButtons = (RadioGroup) findViewById(R.id.repeatButtons);

        //if isCheck is true pause button shows. If False then play button shows
        playButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    mpservice.resumePlay();         //if true
                } else {
                    mpservice.pauseSong();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call and play previous song
                //if song[index-1] would == null, then go to song[highest_index]
                mpservice.playPrevious();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call and play next song
                //if song[index+1] is out of bounds, then go to song[index_1]
                mpservice.playNext();
            }
        });
        repeatButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int id) {
                System.out.println("We're in onCheckedChanged!!");
                RadioButton rb=(RadioButton)findViewById(id);
                mpservice.setLoop(getResources().getResourceEntryName(id));
            }
        });

    }
}