package com.example.matt.chromesthesia;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.content.*;
import android.widget.ProgressBar;
import java.util.ArrayList;

/**
 * Created by matt & will on 10/1/2016.
 */

public class Library extends Fragment {

    public Library(){}

    private LayoutInflater layoutInf;
    private ArrayList<Song> songs;
    private ArrayList<String> songArray;
    private ListView songView;
    private ImageAdapter imgAdapter;
    static ArrayAdapter<String> listAdapter;
    private ProgressBar progressB;
    public MPC mpservice;
    Chromesthesia chromesthesia;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        chromesthesia = (Chromesthesia) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.libraryscreen, container, false);
        createMusicList();
        songView = (ListView)rootView.findViewById(R.id.librarylist);
        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.arow) {};

        System.out.println("PRINTING OUT OUR SONGARRAY");
        final GridView gridview = (GridView)rootView.findViewById(R.id.libraryGridView);
        gridview.setAdapter(listAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position == 0){
                    chromesthesia.mpservice.playPrevious();
                }
                if(position == 1){
                    System.out.println("we're in resumeplay!!!!");
                    chromesthesia.mpservice.resumePlay();
                }
                if(position == 2){
                    chromesthesia.mpservice.pauseSong();
                }
                if(position == 3){
                    chromesthesia.mpservice.playNext();
                }
                if(position == 4){
                    Intent libraryToPlayScreenIntent = new Intent(v.getContext(), NowPlayingScreen.class);
                    v.getContext().startActivity(libraryToPlayScreenIntent);
                }
            }
        });

        progressB = (ProgressBar)rootView.findViewById(R.id.progressB);
        progressB.setMax(100);

        Thread refresh = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {int x = 5;
                                    if (chromesthesia.mpservice != null) {
                                        if (chromesthesia.mpservice.getPosition() != -1 && chromesthesia.mpservice.getDuration() != -1) {
                                            progressB.setProgress((int) (((float) chromesthesia.mpservice.getPosition() / chromesthesia.mpservice.getDuration()) * 100));
                                        }
                                    }
                                }
                            });
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
        songs = chromesthesia.songlist;
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