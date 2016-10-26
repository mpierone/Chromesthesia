package com.example.matt.chromesthesia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.matt.chromesthesia.playlistDev.CreatePlaylistScreen;
import com.example.matt.chromesthesia.playlistDev.Playlist;

import java.util.ArrayList;

/**
 * Created by Matt on 10/8/2016.
 */

public class PlayListSelectionScreen extends Chromesthesia {

    private EditText playlistName;
    private LayoutInflater layoutInf;
    private ArrayList<Playlist> playlists; //list of Playlist objects (with their files) saved by the user

    private ArrayList<String> playlistArray; //list of Playlist NAMES saved by the user
    private ListView listView;
    private ListView playlistView;


    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.playlistscreen);
        playlistView = (ListView)findViewById(R.id.list);

        /*
        System.out.println("PRINTING OUT OUR PLAYLIST ARRAY");
        for (String playlistName : playlistArray) {
            System.out.println(playlistName);
        }
        try
        {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playlistArray);
            playlistView.setAdapter(arrayAdapter);
        }
        catch (NullPointerException e){
            Log.e("Error:","No playlists found.", e);
        }
        playlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playlistArray.get(position);
                //some method for selection of the playlist
            }
        });
        */

        /*Create Playlist Button onClick code; it's really long so I'm surrounding it with comments because my EYES HURT!!!*/
        Button createPlaylist = (Button) findViewById(R.id.buttonPrompt);
        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for opening up another screen with playlist creation stuff
                Intent createIntent = new Intent(context, CreatePlaylistScreen.class);
                startActivityForResult(createIntent,0);
            }
        });
        /*End of create playlist dialog box code*/


    }

    public void createPlaylistList() {
        String playlistName;
        playlists = playlistList;
        System.out.println("size is:");
        System.out.println(playlists.size());
        playlistArray = new ArrayList<>();
        int i = 0;
        try{
            for(Playlist p : playlists) {
                playlistName = p.getPlaylistName();
                System.out.println(playlistName);
                playlistArray.add(playlistName);
            }
        }
        catch (Exception e){
            Log.e("lmm in library.java","stuff broke",e);
        }

    }
    }




