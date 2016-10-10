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

import java.util.ArrayList;
import com.example.matt.chromesthesia.playlistDev.*;

/**
 * Created by Jimmy on 10/8/16.
 */
public class LibraryFragment extends Fragment {
    ListView listView;
    static ArrayAdapter<String> listAdapter;
    static Song song;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.libraryscreen, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);

        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row) {
        };


        listView.setAdapter(listAdapter);
        addSongsToList();
        update();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = ((TextView)view).getText().toString();
                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();


            }
        });

        return rootView;
    }
    public static void addSongsToList(){
        ArrayList<String> songs = new ArrayList<>();
        songs.add(song.get_id3().getTitle() + " - " + song.get_id3().getArtist());
        listAdapter.clear();
        listAdapter.addAll(songs);
    }
    public static void update(){
        listAdapter.notifyDataSetChanged();
    }
}
