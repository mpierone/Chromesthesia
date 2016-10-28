package com.example.matt.chromesthesia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matt.chromesthesia.playlistDev.Playlist;
import com.example.matt.chromesthesia.playlistDev.PlaylistManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Isabelle on 10/25/2016.
 * Screen for editing the contents of a Playlist
 * Comes up when you click "Create" on CreatePlaylistScreen
 * Comes up when you click "Edit" on a selected Playlist's screen
 * */


public class AddSongsToPlaylistScreen extends PlayListSelectionScreen{

    String SD_LOCATION = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
    File sdlocation = new File(SD_LOCATION + "/Download/");
    boolean deviceHasSDCard;
    //private ArrayList<Playlist> playlistList;
    ListView addSongsView;
    private ArrayList<Song> songs;
    public Context addSongsContext;
    public ArrayList<String> songArray;
    public ArrayAdapter arrayAdapter;
    public Playlist pl;
    public AddSongsToPlaylistScreen(){
        //constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsongstoplaylistscreen);
        addSongsView = (ListView)findViewById(R.id.list);
       // TextView textView = (TextView) findViewById(R.id.typeYourMessage);
        final EditText editText = (EditText) findViewById(R.id.inputPlaylistName);
        pl = new Playlist(editText.getText().toString());
        createMusicList();
        System.out.println("PRINTING OUT OUR SONGARRAY");
        for (String s : songArray) {
            System.out.println(s);
        }
        try
        {
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songArray);
            addSongsView.setAdapter(arrayAdapter);
        }
        catch (NullPointerException e){
            Log.e("Error:","No songs in playlist", e);
        }

        addSongsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pl.stringFilenames.add(songlist.get(position).getFilename());
                Toast t = Toast.makeText(playlistContext, "Playlist files: " + pl.stringFilenames,Toast.LENGTH_LONG);
                t.show();

            }
        });

        Button saveExitBtn = (Button) findViewById(R.id.addSongsSave);
        saveExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pl.stringFilenames.add("1234");
                //code for saving the selected songs to the playlist's file.
                PlaylistManager pm = new PlaylistManager();
                try {
                    pm.savePlaylist(pl, editText.getText().toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    pm.savePlaylist(pl, editText.getText().toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //Go back to Playlist Selection Screen:
                Intent goBackToPlaylists = new Intent(playlistContext, PlayListSelectionScreen.class);
                startActivityForResult(goBackToPlaylists,0);
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
                songArray.add(mergedName);
            }
        }
        catch (Exception e){
            Log.e("lmm in library.java","stuff broke",e);
        }

    }

}
