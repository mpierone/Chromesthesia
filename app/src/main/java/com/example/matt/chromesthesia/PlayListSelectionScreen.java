package com.example.matt.chromesthesia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.matt.chromesthesia.playlistDev.Playlist;

import java.util.ArrayList;

/**
 * Created by Matt on 10/8/2016.
 */

public class PlayListSelectionScreen extends Chromesthesia {
/* BROKEN CODE
    private EditText playlistName;
    private LayoutInflater layoutInf;
     //list of Playlist objects (with their files) saved by the user
    protected ArrayList<String> playlistArray; //list of Playlist NAMES saved by the user
    private ListView listView;
    private ListView playlistView;
    public Context playlistContext = this;
    protected Playlist selPlay;

   // private String selectedPlaylist;

    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.playlistscreen);
        playlistView = (ListView)findViewById(R.id.playlistView);

        createPlaylistList();
        System.out.println("PRINTING OUT OUR PLAYLIST ARRAY");
        for (String playlistName : playlistArray) {
            System.out.println(playlistName);
        }
        try
        {
            if (playlistArray == null){
                String emptyAdapterMsg = "Something went wrong, we can't find the playlist array!";
                ArrayList<String> empties = new ArrayList<>();
                empties.add(emptyAdapterMsg);
                ArrayAdapter a = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, empties);
                playlistView.setAdapter(a);
            }
            else{
                ArrayAdapter<String> pArrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, playlistArray);
                playlistView.setAdapter(pArrayAdapter);
            }
        }
        catch (NullPointerException e){
            Log.e("Error:","No playlists found.", e);
        }

        /*On click for selecting a playlist*//*
        playlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPlaylist = playlistArray.get(position);
                System.out.println("!!!!!!in playlist onclick listener:  "+selectedPlaylist);

                Intent openPlaylistContents = new Intent(context, PlaylistContents.class);
                Intent updateSelectedPlaylist = new Intent(context, Chromesthesia.class);
                Intent sendPlaylistToChromesthesia = new Intent(context, Chromesthesia.class);

                String x = playlistArray.get(position);
                openPlaylistContents.putExtra("VALUE", x );
                updateSelectedPlaylist.putExtra("VALUE", x);
                sendPlaylistToChromesthesia.putExtra("PLAYLIST", songlist);

                //startActivity(sendPlaylistToChromesthesia);
                startActivity(updateSelectedPlaylist);
                startActivityForResult(openPlaylistContents,0);

            }
        });

        /*Create Playlist Button onClick code; it's really long so I'm surrounding it with comments because my EYES HURT!!!*//*
        Button createPlaylist = (Button) findViewById(R.id.buttonPrompt);
        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code for opening up another screen with playlist creation stuff
                Intent createIntent = new Intent(context, AddSongsToPlaylistScreen.class);
                startActivityForResult(createIntent,0);
            }
        });
        /*End of create playlist dialog box code*/
/*

    }

    public void createPlaylistList() {
        ArrayList<Playlist> playlists;
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
            Log.e("pm in PSScreen.java","stuff broke",e);
        }

    }
    public String getPlaylist() {
        return selectedPlaylist;
    }
    }*/
}




