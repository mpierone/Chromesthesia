package com.example.matt.chromesthesia;

//import android.icu.util.TimeUnit;
import android.app.Activity;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;
import android.support.v4.app.Fragment;
import android.media.MediaPlayer;
import android.widget.ToggleButton;

import java.util.concurrent.TimeUnit;
import com.example.matt.chromesthesia.MPC;

import org.w3c.dom.Text;


/**
 * Created by Matt on 10/8/2016.
 */

public class NowPlayingScreen extends Fragment{
    private final Handler handler = new Handler();
    SeekBar seekBar;
    public MPC mpservice;
    Chromesthesia chromesthesia;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        chromesthesia = (Chromesthesia) getActivity();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.playscreen, container, false);
        super.onCreate(savedInstanceState);
        final ToggleButton playButton = (ToggleButton) rootView.findViewById(R.id.playButton);
        final ImageButton previousButton = (ImageButton) rootView.findViewById(R.id.previousButton);
        final ImageButton nextButton = (ImageButton) rootView.findViewById(R.id.nextButton);
        final TextView artistName = (TextView) rootView.findViewById(R.id.artistText);
        final TextView totalTime = (TextView) rootView.findViewById(R.id.totalTime);
        final TextView currentTime = (TextView) rootView.findViewById(R.id.currentTime);
        final TextView songTitle = (TextView) rootView.findViewById(R.id.songTitleText);
        final RadioGroup repeatButtons = (RadioGroup) rootView.findViewById(R.id.repeatButtons);

    currentTime.setText("0:00");
    totalTime.setText("X:XX");
    //if isCheck is true pause button shows. If False then play button shows
    playButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!isChecked) {
                chromesthesia.mpservice.resumePlay();         //if true
            } else {
                chromesthesia.mpservice.pauseSong();
            }
        }
    });

    previousButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //call and play previous song
            //if song[index-1] would == null, then go to song[highest_index]
            playButton.setChecked(false);
            chromesthesia.mpservice.playPrevious();
        }
    });

    nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //call and play next song
            //if song[index+1] is out of bounds, then go to song[index_1]
            playButton.setChecked(false);
            chromesthesia.mpservice.playNext();
        }
    });
    repeatButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
    {
        public void onCheckedChanged(RadioGroup group, int id) {
        System.out.println("We're in onCheckedChanged!!");
        RadioButton rb=(RadioButton)rootView.findViewById(id);
        chromesthesia.mpservice.setLoop(getResources().getResourceEntryName(id));
    }
    });

    seekBar = (SeekBar)rootView.findViewById(R.id.seekBar);
    seekBar.setMax(100);
    //seekBar.setOnTouchListener((View.OnTouchListener) seekBar);
    //Thread will refresh seekbar and times every second
    Thread refresh = new Thread() {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Thread.sleep(1000);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int duration = chromesthesia.mpservice.getDuration();
                            int current = chromesthesia.mpservice.getPosition();
                            seekBar.setProgress((int) (((float) chromesthesia.mpservice.getPosition() / chromesthesia.mpservice.getDuration()) * 100));
                            currentTime.setText(displayTime(current));
                            totalTime.setText(displayTime(duration));
                            artistName.setText(chromesthesia.mpservice.getArtist());
                            songTitle.setText(chromesthesia.mpservice.getName());
                        }
                    });


                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    refresh.start();
        return rootView;
    }//end of onCreate

    private void seekBarUpdater() {
        seekBar.setProgress((int) (((float) chromesthesia.mpservice.getPosition() / chromesthesia.mpservice.getDuration()) * 100));
        if (chromesthesia.mpservice.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    seekBarUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.seekBar) {
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
            if (chromesthesia.mpservice.isPlaying()) {
                SeekBar sb = (SeekBar) v;
                int playPositionInMillisecconds = (chromesthesia.mpservice.getDuration() / 100) * sb.getProgress();
                chromesthesia.mpservice.seek(playPositionInMillisecconds);
            }
        }
        return false;
    }

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

