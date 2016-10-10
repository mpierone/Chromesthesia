package com.example.matt.chromesthesia;

import android.app.Fragment;
import android.content.Intent;
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

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Jimmy on 10/8/16.
 */
public class LibraryFragment extends Fragment {
    ListView listView;
    static ArrayAdapter<String> listAdapter;
    static Song song;
    static localMusicManager lmm;
    static ArrayList<Song> songList;
    static File chosenSong;

    public LibraryFragment() throws Exception {lmm= new localMusicManager();songList=lmm.makeSongsList();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.libraryscreen, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);

        listAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_selectable_list_item) {
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
                Intent playScreenIntent = new Intent(view.getContext(), NowPlayingScreen.class);
                startActivityForResult(playScreenIntent, 0);
                chosenSong = pickSong(position);

            }
        });

        return rootView;
    }

    public static File pickSong(int indexOfSongClicked) {
        File songPicked = new File(songList.get(indexOfSongClicked).get_audioFilePath());
        return songPicked;
    }

    public static void addSongsToList() throws Exception {
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
