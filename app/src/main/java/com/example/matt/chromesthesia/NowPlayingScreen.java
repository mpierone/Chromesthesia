package com.example.matt.chromesthesia;

//import android.icu.util.TimeUnit;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.example.matt.chromesthesia.MPC;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import org.w3c.dom.Text;


/**
 * Created by Matt on 10/8/2016.
 */

public class NowPlayingScreen extends Fragment implements View.OnTouchListener, MediaPlayer.OnBufferingUpdateListener, View.OnClickListener{
    private final Handler handler = new Handler();
    SeekBar seekBar;
    private int x = 0;
    public MPC mpservice;
    Chromesthesia chromesthesia;
    private View rootView;
    private ExpandableListView playQueue;
    private playQueueAdapter playAdapter;
    private List<String> header = new ArrayList<>();
    private ImageView albumArt;
    private localMusicManager lmm;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        chromesthesia = (Chromesthesia) getActivity();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.playscreen, container, false);
        super.onCreate(savedInstanceState);
        return rootView;
    }//end of onCreate

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lmm = new localMusicManager();
        final ToggleButton playButton = (ToggleButton) rootView.findViewById(R.id.playButton);
        final ImageButton previousButton = (ImageButton) rootView.findViewById(R.id.previousButton);
        final ImageButton nextButton = (ImageButton) rootView.findViewById(R.id.nextButton);
        final TextView artistName = (TextView) rootView.findViewById(R.id.artistText);
        final TextView totalTime = (TextView) rootView.findViewById(R.id.totalTime);
        final TextView currentTime = (TextView) rootView.findViewById(R.id.currentTime);
        final TextView songTitle = (TextView) rootView.findViewById(R.id.songTitleText);
        final RadioGroup repeatButtons = (RadioGroup) rootView.findViewById(R.id.repeatButtons);
        repeatButtons.check(R.id.ALL);
        albumArt = (ImageView) rootView.findViewById(R.id.albumArt);
        header.add("Now Playing");
        playQueue = (ExpandableListView) rootView.findViewById(R.id.playQueue);
        playAdapter = new playQueueAdapter(rootView.getContext(), header, chromesthesia.playQueueNames);
        playQueue.setAdapter(playAdapter);
        playQueue.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                playAdapter.setplayQueue(chromesthesia.getPlayQueueNames());
            }
        });
        playQueue.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                chromesthesia.playSong(v, childPosition);
                if(chromesthesia.mpservice.isPlaying())
                {
                    TextView songTitle = (TextView) rootView.findViewById(R.id.songTitleText);
                    songTitle.setText(chromesthesia.mpservice.getName());
                    TextView artistName = (TextView) rootView.findViewById(R.id.artistText);
                    artistName.setText(chromesthesia.mpservice.getArtist());
                }
                return true;
            }
        });
        rootView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                playQueue.collapseGroup(0);
                if (hasFocus) {
                    if (chromesthesia.mpservice.isPlaying()) {
                        playButton.setChecked(true);
                    }
                    if (chromesthesia.mpservice.isPaused()) {
                        playButton.setChecked(false);
                    }
                }
            }
        });
        playQueue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    playQueue.collapseGroup(0);
                }
            }
        });
        //playQueue.setVisibility(View.INVISIBLE);
        currentTime.setText("0:00");
        totalTime.setText("X:XX");
        //if isCheck is true pause button shows. If False then play button shows
        playButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (chromesthesia.nowPlaying.size() == 0) {
                        chromesthesia.mpservice.setSngs(chromesthesia._OGsongs);
                        chromesthesia.playQueueNames = lmm.makeSongNames(chromesthesia.mpservice.getSongs());
                        playAdapter.setplayQueue(chromesthesia.getPlayQueueNames());
                        chromesthesia.mpservice.position = 0;
                        chromesthesia.mpservice.datachanged = true;
                        chromesthesia.mpservice.playsong();
                    }
                    else {
                        chromesthesia.mpservice.resumePlay();         //if true
                    }
                } else {
                    chromesthesia.mpservice.pauseSong();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chromesthesia.mpservice.getSongs().size() == 0) {

                }
                else if (chromesthesia.mpservice.isPaused()) {
                    playButton.setChecked(true);
                    chromesthesia.mpservice.playPrevious();
                }
                else if (chromesthesia.mpservice.getPosition() > 5000) {
                    playButton.setChecked(true);
                    chromesthesia.mpservice.playsong();
                }
                else {
                    playButton.setChecked(true);
                    chromesthesia.mpservice.playPrevious();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chromesthesia.mpservice.getSongs().size() == 0) {

                }
                else if (chromesthesia.mpservice.isPaused()) {
                    playButton.setChecked(true);
                    chromesthesia.mpservice.playNext();
                }
                else {
                    playButton.setChecked(true);
                    chromesthesia.mpservice.playNext();
                }
            }
        });
        repeatButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int id) {
                if (id == R.id.ALL) {
                    repeatButtons.check(R.id.ALL);
                    chromesthesia.mpservice.setLoop("ALL");
                }
                else if (id == R.id.NONE) {
                    repeatButtons.check(R.id.NONE);
                    chromesthesia.mpservice.setLoop("NONE");
                }
                else if (id == R.id.ONE) {
                    repeatButtons.check(R.id.ONE);
                    chromesthesia.mpservice.setLoop("ONE");
                }
            }
        });

        seekBar = (SeekBar)rootView.findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setOnTouchListener(this);
        seekBar.setOnClickListener(this);
        /*seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(rootView.getContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(rootView.getContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(rootView.getContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                chromesthesia.mpservice.seek(progress);
            }
        });*/

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
                            if (chromesthesia.mpservice.prepared) {
                                if (chromesthesia.mpservice.artchanged) {
                                    albumArt.setImageBitmap(chromesthesia.mpservice.albumArt);
                                    //chromesthesia.mpservice.artchanged = false;
                                }
                                if (chromesthesia.mpservice.albumArt == null) {
                                    albumArt.setImageResource(R.drawable.defalbumart1);
                                    x = 1;
                                }
                                if (chromesthesia.mpservice.isPlaying()) {
                                    playButton.setChecked(true);
                                }
                                int duration = chromesthesia.mpservice.getDuration();
                                int current = chromesthesia.mpservice.getPosition();
                                seekBar.setProgress((int) (((float) chromesthesia.mpservice.getPosition() / chromesthesia.mpservice.getDuration()) * 100));
                                currentTime.setText(displayTime(current));
                                totalTime.setText(displayTime(duration));
                                artistName.setText(chromesthesia.mpservice.getArtist());
                                songTitle.setText(chromesthesia.mpservice.getName());
                            }
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
    }

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
        int playPositionInMillisecconds = 0 ;
        if (v.getId() == R.id.seekBar) {
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
            if (chromesthesia.mpservice.isPlaying() || chromesthesia.mpservice.isPaused()) {
                SeekBar sb = (SeekBar) v;
                if (event.ACTION_DOWN == 0) {
                    sb.setProgress(((int) ((sb.getMax() * event.getX()) / v.getWidth())));
                    playPositionInMillisecconds = (chromesthesia.mpservice.getDuration() / 100) * sb.getProgress();
                } else
                    playPositionInMillisecconds = (chromesthesia.mpservice.getDuration() / 100) * sb.getProgress();
            }
                chromesthesia.mpservice.seek(playPositionInMillisecconds);
        }
        return false;
    }
    public void onClick(View v) {
        if (v.getId() == R.id.seekBar) {
            if (chromesthesia.mpservice.isPlaying() || chromesthesia.mpservice.isPaused()) {
                SeekBar sb = (SeekBar) v;
                int pos = (chromesthesia.mpservice.getDuration() / 100) * sb.getProgress();
                chromesthesia.mpservice.seek(pos);
            }
        }
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

