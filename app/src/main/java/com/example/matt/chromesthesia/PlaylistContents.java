package com.example.matt.chromesthesia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.chromesthesia.playlistDev.Playlist;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Isabelle on 10/28/2016.
 */

public class PlaylistContents extends PlayListSelectionScreen {
    ListView contentsView;
    TextView playlistName;
    //ArrayList<Song> _playlistSongs;
    ArrayList<String> psongArray;
    Chromesthesia chromesthesia;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.libraryscreen, container, false);
        playlistName = (TextView) rootView.findViewById(R.id.playlistName);
        contentsView = (ListView) rootView.findViewById(R.id.playlistContents);
        //playlistName.setText();
        String pl = " ";

        System.out.println("playlistContents.java selectedPlaylist:  "+ pl);
        selPlay = new Playlist(pl);

        /*Populating playlist songs array*/
        localMusicManager lmm = new localMusicManager();
        try {
            populatePlaylist(lmm.getSD_LOCATION());
            createSongArray();
            for (Song s : selPlay._playlistSongs){
                System.out.println("Added " + s.get_id3().getTitle() + " by " + s.get_id3().getArtist() + " to playlist: " +pl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(rootView.getContext(), "Could not populate playlist!", Toast.LENGTH_LONG);
        }

        /*Code for making a string array of Titles and Names of songs in playlist and setting
        * the Array Adapter*/



        try
        {
            if (psongArray == null){
                String emptyAdapterMsg = "Something went wrong, we can't find the playlist's songs array!";
                System.out.println(selPlay._playlistSongs);
                ArrayList<String> empties = new ArrayList<>();
                empties.add(emptyAdapterMsg);
                ArrayAdapter a = new ArrayAdapter<>(chromesthesia.getBaseContext(), android.R.layout.simple_list_item_1, empties);
                contentsView.setAdapter(a);
            }
            else{
                ArrayAdapter<String> pArrayAdapter = new ArrayAdapter<String>(chromesthesia.getBaseContext(), android.R.layout.simple_list_item_1, psongArray);
                contentsView.setAdapter(pArrayAdapter);
            }
        }
        catch (NullPointerException e){
            Log.e("Error:","No playlists found.", e);
        }
        /*
        System.out.println("PRINTING OUT OUR PLAYLIST'S SONGS ARRAY");
        System.out.println("Size: " + songArray.size());
        for (String song : songArray) {
            System.out.println(song);
        }

        System.out.println("PRINTING OUT OUR PLAYLIST'S SONG FILES");
        System.out.println("FILES FOUND: " + selPlay.stringFilenames.size());
        for (String s: selPlay.stringFilenames){
            System.out.println(s);
        }

        System.out.println("PRINTING OUT OUR PLAYLIST'S SONG OBJECTS");
        /*System.out.println("SONGS FOUND: " + selPlay._playlistSongs.size());
        for (Song s: selPlay._playlistSongs){
            System.out.println(s.get_audioFilePath());
        }
*/
        /*On click for selecting a song*/
        contentsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //code for getting play functionality
                psongArray.get(position);
                chromesthesia.playSong(view, position);
            }
        });

    return rootView;
    }


    /*Taking the loaded playlist and checking if a file found on the SDCard is in the playlist
   * false = not in playlist, so don't include in the Playlist object's songs
   * true = in playlist, add the song to the Playlist object's songs*/
    private boolean boolAddToPlaylist(String sdFilename, Playlist p) throws FileNotFoundException {
        return p.getFilenamesArray().contains(sdFilename);
    }

    /*Add songs to playlist if they are supposed to be there*/

    public ArrayList<Song> populatePlaylist(String folder) throws Exception {
        try {
            selPlay.stringFilenames = selPlay.loadPlaylist();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            File sdCard = new File(folder);
            if (sdCard.listFiles().length > 0) {
                for (File file : sdCard.listFiles()){
                    /*Recursively call to find subfolders*/
                    if (file.isDirectory()) {
                        populatePlaylist(file.getAbsolutePath());
                    }
                    /*here is where we'll find our songs stored in a text file
                    * if the Arraylist<> read from the txt file contains the filename found on the SD Card, add it to the songsList
                    * If not, then skip it
                    * */
                    else if ((file.getAbsolutePath().toLowerCase().endsWith(".mp3") || file.getAbsolutePath().toLowerCase().endsWith(".flac"))
                            && boolAddToPlaylist(file.getName(), selPlay) == true
                            ) {
                        Song so = new Song(file.getAbsolutePath());
                        so.set_filename(file.getName());
                        selPlay._playlistSongs.add(so);

                        if (so == null) {
                            System.out.println("so == null!\n");
                        }
                        //String thetitle = so.get_id3().getTitle();
                        //System.out.println(thetitle);
                        //System.out.println(so.get_id3().getArtist());
                        //System.out.println(so.get_id3().getAlbum());

                    }
                }
            }
            //System.out.println(_songsList.size());
        } catch (Exception e) {
            Log.e("lmm", "err setting datasource", e);
        }
        return selPlay._playlistSongs;
    }

    public void createSongArray() {
        String songName;
        String artistName;
        String mergedName;
        psongArray = new ArrayList<>();
        int i = 0;
        try{
            for(Song s : selPlay._playlistSongs) {
                songName = s.get_id3().getTitle();
                artistName = s.get_id3().getArtist();
                mergedName = songName + " - " + artistName;
                System.out.println(mergedName);
                psongArray.add(mergedName);
            }
        }
        catch (Exception e){
            Log.e("pm in PSScreen.java","stuff broke",e);
        }
  //      System.out.println("size is:");
//        System.out.println(selPlay._playlistSongs.size());
    }

}
