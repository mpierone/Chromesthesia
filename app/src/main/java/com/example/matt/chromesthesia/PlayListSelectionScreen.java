package com.example.matt.chromesthesia;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    Button createPlaylist;
    private EditText playlistName;
    private LayoutInflater layoutInf;
    private ArrayList<Playlist> playlists; //list of Playlist objects (with their files) saved by the user

    private ArrayList<String> playlistArray; //list of Playlist NAMES saved by the user
    private ListView listView;
    private ListView playlistView;


    public void onCreate(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlistscreen);
        playlistView = (ListView)findViewById(R.id.librarylist);
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
    }

    public void createPlaylistList() {
        String playlistName;
        playlists = playlistList;
        System.out.println("in library.java and size is:");
        System.out.println(playlists.size());
        playlistArray = new ArrayList<>();
        //String[] songArray = new String[songs.size()];
        //String[] sampleArray = {"1", "2", "3"};
        //sampleArray = {"1", "2", "3"};
        int i = 0;
        try{
            for(Playlist p : playlists) {
                playlistName = p.getPlaylistName();
                System.out.println(playlistName);
                //songArray[i] = (mergedName);
                playlistArray.add(playlistName);
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




        /*Code to set up playlist creation as a dialog box:
        createPlaylist.setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            @Override
            public void onClick(View v) {
                {
                    // Use the Builder class for convenient dialog construction

                    // set prompts.xml to alertdialog builder
                    builder.setView(R.layout.playlistprompt);

                    //Stores user input as playlistName
                    playlistName = (EditText) findViewById(R.id.editTextResult);
                    final EditText userInput = (EditText) findViewById(R.id.editTextDialogUserInput);

                    //Sets up how the dialog box will appear:
                    builder.setTitle("Create Playlist");
                    builder.setMessage("Enter a name for this new playlist:")
                            .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    playlistName.setText(userInput.getText());
                                }
                            })
                            .setCancelable(true);

                }
                // Create the AlertDialog object and return it
                AlertDialog createPlay = builder.create();
                createPlay.show();
            };

        });*/





