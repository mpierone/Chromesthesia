package com.example.matt.chromesthesia;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.media.MediaPlayer;
import android.widget.ToggleButton;

import com.example.matt.chromesthesia.MPC;

import org.w3c.dom.Text;


/**
 * Created by Matt on 10/8/2016.
 */

public class NowPlayingScreen extends Chromesthesia implements View.OnTouchListener {
    private SeekBar seekBar;
    private final Handler handler = new Handler();
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.playscreen);
        final ToggleButton playButton = (ToggleButton) findViewById(R.id.playButton);
        final ImageButton previousButton = (ImageButton) findViewById(R.id.previousButton);
        final ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
        TextView songTitle = (TextView) findViewById(R.id.songTitleText);
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

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(99);
        seekBar.setOnTouchListener(this);

    }   //end of onCreate

    private void seekBarUpdater() {
        seekBar.setProgress((int) (((float) mpservice.getPosition() / mpservice.getDuration()) * 100));
        if (mpservice.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    seekBarUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    @Override       //allows for user to touch progress bar to skip ahead/back in a song
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.seekBar) {
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
            if (mpservice.isPlaying()) {
                SeekBar sb = (SeekBar) v;
                int playPositionInMillisecconds = (mpservice.getDuration() / 100) * sb.getProgress();
                mpservice.seek(playPositionInMillisecconds);
            }
        }
        return false;
    }

}//end of class

