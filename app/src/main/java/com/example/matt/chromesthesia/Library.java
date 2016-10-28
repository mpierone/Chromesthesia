package com.example.matt.chromesthesia;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import java.util.ArrayList;

/**
 * Created by matt & will on 10/1/2016.
 */

public class Library extends Chromesthesia {
    private LayoutInflater layoutInf;
    private ArrayList<Song> songs;
    protected ArrayList<String> songArray;
    private ListView listView;
    private ListView songView;
    public ArrayAdapter<String> arrayAdapter;
    localMusicManager lMM = new localMusicManager();
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        createMusicList();
        setContentView(R.layout.libraryscreen);
        songView = (ListView)findViewById(R.id.librarylist);
        System.out.println("PRINTING OUT OUR SONGARRAY");
        for (String s : songArray) {
            System.out.println(s);
        }
        try
        {
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songArray);
            songView.setAdapter(arrayAdapter);
        }
        catch (NullPointerException e){
            Log.e("Error:","No songs in playlist", e);
        }
        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                songArray.get(position);
                playSong(view, position);
            }
        });
    }

    public void createMusicList() {
        String songName;
        String artistName;
        String mergedName;
        songs = songlist;
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