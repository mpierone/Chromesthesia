package com.example.matt.chromesthesia;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.matt.chromesthesia.playlistDev.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimmy & matt on 10/1/2016.
 */

public class Library extends Activity {
    ArrayList<Song> libraryList;
    List<String> songs = new ArrayList<>();
    private SongAdapter songAdapter;
    ArrayList<Song> songArray = new ArrayList<>();
    localMusicManager lMM = new localMusicManager();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libraryscreen);
        try {
            libraryList = lMM.makeSongsList();
            for (int i = 0; i < libraryList.size(); i++) {
                String songName = libraryList.get(i).get_id3().getTitle();
                String artistName = libraryList.get(i).get_id3().getArtist();
                System.out.println(songName);
                System.out.println(artistName);
                songArray.add(libraryList.get(i));
            }
        }
        catch(Exception e){
            Log.e("Error: ", "No song in list", e);
        }
        String[] songArray = {"Song 1", "Song 2"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songArray);
        ListView libView = (ListView) findViewById(R.id.list);
        libView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

}