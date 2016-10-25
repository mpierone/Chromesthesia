package com.example.matt.chromesthesia;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.chromesthesia.playlistDev.Playlist;

import static com.example.matt.chromesthesia.LibraryFragment.addSongsToList;
import static com.example.matt.chromesthesia.LibraryFragment.update;

/**
 * Created by Isabelle on 10/25/2016.
 * This is for selecting which playlist to play, NOT selecting a song from a playlist.
 */

public class PlayListSelectionFragment extends Fragment {
    ListView listView;
    static ArrayAdapter<String> listAdapter;
    static Song song;
    static Song chosenSong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.playlistscreen, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);

        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row) {
        };


        listView.setAdapter(listAdapter);
        addSongsToList();
        update();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String item = ((TextView)view).getText().toString();
                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Playlist pl = new Playlist(item);
                        try {
                           /*Code for selecting a playlist and opening that playlist's new screen
                           ON CLICK: Open a Playlist Screen showing only songs loaded from the saved playlist. (If no songs added show "No songs in playlist")
                           See the Playlist.java class for how it looks through SD card and only grabs the specified songs listed in the playlist's txt doc
                            */
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

            }
        });

        return rootView;
    }
}
