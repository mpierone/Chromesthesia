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

import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import java.util.ArrayList;

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
        try {
            addSongsToList();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public static void addSongsToList() throws Exception {
        localMusicManager lmm= new localMusicManager();
        ArrayList<String> visibleSongList = new ArrayList<>();
        for (Song s : lmm.makeSongsList()){
            visibleSongList.add(s.get_id3().getTitle() + "\nBy: " + s.get_id3().getArtist());
        }
        listAdapter.clear();
        listAdapter.addAll(visibleSongList);
    }
    public static void update(){
        listAdapter.notifyDataSetChanged();
    }
}
