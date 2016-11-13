package com.example.matt.chromesthesia;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import com.example.matt.chromesthesia.playlistDev.*;
import android.content.*;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by matt & will on 10/1/2016.
 */

public class Library extends Fragment {

    public Library(){}

    private LayoutInflater layoutInf;
    private ArrayList<Song> songs;
    private ArrayList<String> songArray;
    private ListView listView;
    private ListView songView;
    private ImageAdapter imgAdapter;
    private ProgressBar progressB;
    public MPC mpservice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.libraryscreen, container, false);
        createMusicList();
        songView = (ListView)rootView.findViewById(R.id.librarylist);
        System.out.println("PRINTING OUT OUR SONGARRAY");
        final GridView gridview = (GridView)rootView.findViewById(R.id.libraryGridView);
        gridview.setAdapter(imgAdapter = new ImageAdapter(gridview.getContext()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position == 0){
                    mpservice.playPrevious();
                }
                /*
                if(mpservice.isPlaying()){
                    imgAdapter.mThumbIds[1] = R.drawable.pausebutton;
                }
                else{
                    imgAdapter.mThumbIds[1] = R.drawable.playbuttonunpressed;

                }
                */
                if(position == 1){
                    mpservice.resumePlay();
                    //imgAdapter.mThumbIds[1] = R.drawable.playbuttonunpressed;
                    //System.out.println(Integer.toString(imgAdapter.mThumbIds[1]));
                }
                if(position == 2){
                    mpservice.pauseSong();
                    //imgAdapter.mThumbIds[1] = R.drawable.pausebutton;
                    //System.out.println(Integer.toString(imgAdapter.mThumbIds[1]));

                }
                if(position == 3){
                    mpservice.playNext();
                }
                if(position == 4){
                    Intent libraryToPlayScreenIntent = new Intent(v.getContext(), NowPlayingScreen.class);
                    startActivity(libraryToPlayScreenIntent);
                }
            }
        });

        progressB = (ProgressBar)rootView.findViewById(R.id.progressB);
        progressB.setMax(100);
        Thread refresh = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressB.setProgress((int) (((float) mpservice.getPosition() / mpservice.getDuration()) * 100));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        refresh.start();

    return rootView;
    }

    public void createMusicList() {
        String songName;
        String artistName;
        String mergedName;
        System.out.println("in library.java and size is:");
        System.out.println(songs.size());
        songArray = new ArrayList<>();
        //String[] songArray = new String[songs.size()];
        //String[] sampleArray = {"1", "2", "3"};
        //sampleArray = {"1", "2", "3"};
        int i = 0;
        try{
            for(Song s : songs) {
                songName = s.get_id3().getTitle();
                artistName = s.get_id3().getArtist();
                mergedName = songName + " - " + artistName;
                System.out.println(mergedName);
                //songArray[i] = (mergedName);
                songArray.add(mergedName);
                //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sampleArray);
                //ListView libView = (ListView) findViewById(R.id.librarylist);
                //libView.setAdapter(arrayAdapter);
                //arrayAdapter.notifyDataSetChanged();
                //i++;
            }
        }
        catch (Exception e){
            Log.e("lmm in library.java","stuff broke",e);
        }

    }
}