package com.example.matt.chromesthesia;

//import android.icu.util.TimeUnit;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;
import android.media.MediaPlayer;
import android.widget.ToggleButton;

import java.util.concurrent.TimeUnit;
import com.example.matt.chromesthesia.MPC;

import org.w3c.dom.Text;


/**
 * Created by Matt on 10/8/2016.
 */

public class NowPlayingScreen extends Chromesthesia implements View.OnTouchListener, MediaPlayer.OnBufferingUpdateListener {
    SeekBar seekBar;
    private final Handler handler = new Handler();
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.playscreen);
        final ToggleButton playButton = (ToggleButton) findViewById(R.id.playButton);
        final ImageButton previousButton = (ImageButton) findViewById(R.id.previousButton);
        final ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
        final TextView artistName = (TextView) findViewById(R.id.artistText);
        final TextView totalTime = (TextView) findViewById(R.id.totalTime);
        final TextView currentTime = (TextView) findViewById(R.id.currentTime);
        final TextView songTitle = (TextView) findViewById(R.id.songTitleText);
        final RadioGroup repeatButtons = (RadioGroup) findViewById(R.id.repeatButtons);

        currentTime.setText("0:00");
        totalTime.setText("X:XX");
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
                playButton.setChecked(false);
                mpservice.playPrevious();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call and play next song
                //if song[index+1] is out of bounds, then go to song[index_1]
                playButton.setChecked(false);
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

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setOnTouchListener(this);
        //Thread will refresh seekbar and times every second
        Thread refresh = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int duration = mpservice.getDuration();
                                int current = mpservice.getPosition();
                                seekBar.setProgress((int) (((float) mpservice.getPosition() / mpservice.getDuration()) * 100));
                                currentTime.setText(displayTime(current));
                                totalTime.setText(displayTime(duration));
                                artistName.setText(mpservice.getArtist());
                                songTitle.setText(mpservice.getName());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        refresh.start();

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

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
    }

    //function to get times to display nicely
    public String displayTime(int time) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }


}//end of class

