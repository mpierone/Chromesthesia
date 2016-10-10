package com.example.matt.chromesthesia;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.example.matt.chromesthesia.playlistDev.*;

import java.util.ArrayList;

/**
 * Created by matt on 10/1/2016.
 */

public class Library extends ListActivity {
    localMusicManager lMM;
    View listView = findViewById(R.id.librarylist);

    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.libraryscreen);
        lMM = new localMusicManager();
        createMusicList();
    }

    public void createMusicList(){
        String songName;
        String artistName;
        String mergedName;
        ArrayList<Song> libraryList;
        try{
            libraryList = lMM.makeSongsList();
            for(int i = 0; i < libraryList.size(); i++) {
                libraryList.get(i);
                songName = libraryList.get(i).get_id3().getTitle();
                artistName = libraryList.get(i).get_id3().getArtist();
                mergedName = songName + " - " + artistName;
                listView.setTag(mergedName);
            }
        }
        catch (Exception e){
            Log.e("lmm in library.java","stuff broke",e);

        }
    }
}