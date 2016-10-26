package com.example.matt.chromesthesia.playlistDev;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.matt.chromesthesia.PlayListSelectionScreen;
import com.example.matt.chromesthesia.R;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Isabelle on 10/26/2016.
 */

public class CreatePlaylistScreen extends PlayListSelectionScreen {
    View createPlaylistView;

    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.playlistprompt);
        TextView textView = (TextView) findViewById(R.id.typeYourMessage);
        final EditText editText = (EditText) findViewById(R.id.inputPlaylistName);
        Button createGo = (Button) findViewById(R.id.btnCreatePlaylist);


        /*on click for button*/
        createGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playlistName = editText.getText().toString();
                Playlist p = new Playlist(playlistName, new File(playlistName + ".txt"));
                try {
                    p.savePlaylist();
                } catch (FileNotFoundException e) {
                    System.out.println("Couldn't save the playlist text file.");
                    e.printStackTrace();
                }
            }
        });
    }
}
