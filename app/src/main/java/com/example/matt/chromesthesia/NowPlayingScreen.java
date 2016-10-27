package com.example.matt.chromesthesia;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.media.MediaPlayer;
import android.widget.ToggleButton;
import android.content.Intent;
/**
 * Created by Matt on 10/8/2016.
 */

public class NowPlayingScreen extends Chromesthesia {

    //private Intent player;
    protected TextView artistName;
    protected TextView songTitle;

    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.playscreen);

        if(mpservice != null) {
            updatePlayScreenInfo();
        }
        if (mpservice==null) {
            System.out.println("NOWPLAYINGSCREEN AND SERVICE OBJECT IS NULL");
        }
        artistName = (TextView) findViewById(R.id.artistname);
        songTitle = (TextView) findViewById(R.id.artistname);
        artistName.setText("");
        songTitle.setText("");
        final ToggleButton playButton = (ToggleButton) findViewById(R.id.playButton);
        final ImageButton previousButton = (ImageButton) findViewById(R.id.previousButton);
        final ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);


        //if isCheck is true pause button shows. If False then play button shows
        playButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    mpservice.resumePlay();         //if true
                    updatePlayScreenInfo();
                    System.out.println(mpservice.isPlaying());
                } else {
                    mpservice.pauseSong();
                    updatePlayScreenInfo();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call and play previous song
                //if song[index-1] would == null, then go to song[highest_index]
                mpservice.playPrevious();
                updatePlayScreenInfo();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call and play next song
                //if song[index+1] is out of bounds, then go to song[index_1]
                mpservice.playNext();
                updatePlayScreenInfo();
            }
        });

    }

        public void updatePlayScreenInfo(){
        songTitle.setText(mpservice.getCurrentSongTitle());
        artistName.setText(mpservice.getCurrentArtistName());
    }


}