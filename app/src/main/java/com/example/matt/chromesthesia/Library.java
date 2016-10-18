package com.example.matt.chromesthesia;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.matt.chromesthesia.playlistDev.*;
import android.content.*;

import java.util.ArrayList;

/**
 * Created by matt & will on 10/1/2016.
 */

public class Library extends Chromesthesia {
    private LayoutInflater layoutInf;
    private ArrayList<Song> songs;
    private ListView listView;
    private ListView songView;
    localMusicManager lMM = new localMusicManager();
    public void onCreate(Bundle savedInstancedState) {
        //System.out.println(songlist.get(0).get_id3().getTitle());

        try
        {
            SongAdapter songAdt = new SongAdapter(this, songlist);
            songView.setAdapter(songAdt);
        }
        catch (NullPointerException e){
            Log.e("Error:","No songs in playlist", e);
        }
        super.onCreate(savedInstancedState);
        setContentView(R.layout.libraryscreen);
        createMusicList();
    }
    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createMusicList();
        try
        {
            SongAdapter songAdt = new SongAdapter(this, songlist);
            songView.setAdapter(songAdt);
        }
        catch (NullPointerException e){
            Log.e("Error:","No songs in playlist", e);
        }
        View rootView = inflater.inflate(R.layout.libraryscreen, container, false);
        listView = (ListView)rootView.findViewById( R.id.librarylist);
        return rootView;
    }*/
    public void createMusicList(){
        String songName;
        String artistName;
        String mergedName;
        ArrayList<Song> libraryList = new ArrayList<>();
        System.out.println("in library.java and size is:");
        System.out.println(libraryList.size());
        String[] songArray = new String[libraryList.size()];
        try{
            libraryList = lMM.makeSongsList();
            for(int i = 0; i < libraryList.size(); i++) {
                libraryList.get(i);
                songName = libraryList.get(i).get_id3().getTitle();
                artistName = libraryList.get(i).get_id3().getArtist();
                mergedName = songName + " - " + artistName;
                System.out.println(mergedName);
                songArray[i] = mergedName;
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songArray);
                ListView libView = (ListView) findViewById(R.id.librarylist);
                libView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        }
        catch (Exception e){
            Log.e("lmm in library.java","stuff broke",e);
        }
    }
}