package com.example.matt.chromesthesia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.matt.chromesthesia.playlistDev.CreatePlaylistScreen;

import java.io.File;

/**
 * Created by Isabelle on 10/25/2016.
 * Screen for editing the contents of a Playlist
 * Comes up when you click "Create" on CreatePlaylistScreen
 * Comes up when you click "Edit" on a selected Playlist's screen
 * */


public class AddSongsToPlaylistScreen extends CreatePlaylistScreen{

    String SD_LOCATION = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
    File sdlocation = new File(SD_LOCATION + "/Download/");
    boolean deviceHasSDCard;
    //private ArrayList<Playlist> playlistList;
    ListView addSongsView;
    public Context addSongsContext;
    public AddSongsToPlaylistScreen(){
        //constructor
        addSongsContext = this;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsongstoplaylistscreen);
        addSongsView = (ListView)findViewById(R.id.addsongsLibrary);


        Button saveExitBtn = (Button) findViewById(R.id.addSongsSave);
        saveExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for saving the selected songs to the playlist's file.

                //Go back to Playlist Selection Screen:
                Intent goBackToPlaylists = new Intent(playlistContext, PlayListSelectionScreen.class);
                startActivityForResult(goBackToPlaylists,0);
            }
        });


        Library lib = new Library();
        addSongsView.setAdapter(lib.arrayAdapter);
    }


}
