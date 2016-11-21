package com.example.matt.chromesthesia;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by matt & will on 10/1/2016.
 */

public class Library_title extends Fragment {

    public Thread refresh;
    private View rootView;
    private LayoutInflater layoutInf;
    public ArrayList<Song> songs;
    private ArrayList<String> songArray;
    private ListView songViewtitle;
    private ImageAdapter imgAdapter;
    static ArrayAdapter<String> listAdapter;
    private ProgressBar progressB;
    private GridView gridview;
    //public MPC mpservice;
    Chromesthesia chromesthesia;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        chromesthesia = (Chromesthesia) getActivity();
        songs = chromesthesia.songlist_title;
        localMusicManager lmm = new localMusicManager();
        songArray =  lmm.makeSongNames(chromesthesia.songlist_title);
        System.out.println("YO WE IN LIBRARY TITLE AND OUR TITLE LIST IS:");
        for (String s : songArray) {
            System.out.println(s);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.librarytitlescreen, container, false);
        //songArray = chromesthesia.playQueueNames;
        songViewtitle = (ListView)rootView.findViewById(R.id.librarytitlelist);
        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.arow) {};
        System.out.println("PRINTING OUT OUR SONGARRAY");
        progressB = (ProgressBar)rootView.findViewById(R.id.progressB);
        progressB.setMax(100);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        songViewtitle = (ListView) rootView.findViewById(R.id.librarytitlelist);
        songViewtitle.setAdapter(new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, new ArrayList(songArray)));
        songViewtitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chromesthesia.mpservice.setSngs(songs);
                chromesthesia.nowPlaying = songs;
                chromesthesia.playQueueNames = songArray;
                chromesthesia.playSong(view, position);

                if(chromesthesia.mpservice.isPlaying())
                {
                    TextView songTitle = (TextView) rootView.findViewById(R.id.songTitleText);
                    songTitle.setText(chromesthesia.mpservice.getName());
                    songTitle.setTextColor(Color.BLUE);
                    songTitle.setTextSize(15);
                    TextView artistName = (TextView) rootView.findViewById(R.id.artistText);
                    artistName.setText(chromesthesia.mpservice.getArtist());
                    artistName.setTextColor(Color.BLUE);
                    songTitle.setTextSize(15);
                }
            }
        });
        registerForContextMenu(songViewtitle);
        /*songViewtitle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(rootView.getContext(), songViewtitle.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                return false;
            }
        });*/
        refresh = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (chromesthesia.mpservice.prepared) {
                                progressB.setProgress((int) (((float) chromesthesia.mpservice.getPosition() / chromesthesia.mpservice.getDuration()) * 100));
                            }
                        }
                    });
                }
            }
        };
        refresh.start();
        gridview = (GridView)rootView.findViewById(R.id.libraryGridView);
        gridview.setAdapter(imgAdapter = new ImageAdapter(rootView.getContext()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position == 0){
                    chromesthesia.mpservice.playPrevious();
                }
                if(position == 1){
                    System.out.println("we're in resumeplay!!!!");
                    chromesthesia.mpservice.resumePlay();
                }
                if(position == 2){
                    chromesthesia.mpservice.pauseSong();
                }
                if(position == 3){
                    chromesthesia.mpservice.playNext();
                }
            }
        });

    }

    public void createMusicList() {
        String songName;
        String artistName;
        String mergedName;
        songs = chromesthesia.songlist;
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
                if (artistName == "null") {
                    artistName = "Unknown Artist";
                }
                mergedName = songName + " - " + artistName;
                if (songName == "null") {
                    File f = new File(s.get_audioFilePath());
                    mergedName = f.getName();
                }
                System.out.println(mergedName);
                //songArray[i] = (mergedName);
                songArray.add(mergedName);
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
        chromesthesia.playQueueNames = songArray;
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(3, v.getId(), 9, "Add to Now Playing Queue");
        menu.add(3, v.getId(), 10, "Play Next");
        menu.add(3, v.getId(), 11, "Add to Playlist");
    }
    public boolean onContextItemSelected(MenuItem item){
        if (item.getGroupId() != 3) {return false;}
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ArrayList<Song> sngs = new ArrayList(songs);
        ArrayList<String> nms = new ArrayList(songArray);
        if(item.getTitle()=="Add to Now Playing Queue") {
            int x = info.position;
            Song s = null;
            String name = new String(nms.get(x));
            
            try {
                System.out.println(sngs.get(x).get_audioFilePath());
                s = new Song(sngs.get(x));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            chromesthesia.mpservice.addSong(x, s);
            chromesthesia.playQueueNames.add(name);
            Toast.makeText(rootView.getContext(), "Add to Now Playing Queue Clicked & Pos = " + info.position, Toast.LENGTH_LONG).show();
        }
        if(item.getTitle()=="Play Next") {

            int x = info.position;
            int y = chromesthesia.mpservice.songposition + 1;
            Song s = null;
            String name = new String(songArray.get(x));

            try {
                s = new Song(sngs.get(x));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (chromesthesia.mpservice.getSongs().size() == 0) {
                y = 0;
            }

            chromesthesia.mpservice.addSong(x, y, s);

            chromesthesia.playQueueNames.add(y, name);
        }
        if(item.getTitle()=="Add to Playlist"){
            Toast.makeText(rootView.getContext(), "Add to Playlist", Toast.LENGTH_LONG).show();
        }
        songs = sngs;
        songArray = nms;
        return true;
    }
}