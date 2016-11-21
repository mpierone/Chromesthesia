package com.example.matt.chromesthesia.playlistDev;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.matt.chromesthesia.R;
import com.example.matt.chromesthesia.Song;
import com.example.matt.chromesthesia.Chromesthesia;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by Isabelle on 10/4/2016.
 *
 * This class reads the files from the SD card.
 * Detects any audio files
 */




public class localMusicManager {


    final String SD_LOCATION = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
    File sdlocation = new File(SD_LOCATION + "/Download/");

    private ArrayList<Song> _songsList;
    private ArrayList<String> songArray;
    public localMusicManager() {
        //constructor
        _songsList = new ArrayList<>();
        songArray = new ArrayList<>();
    }

    /*
    Detects audio files with extensions: .mp3, .mp4a, and .wav
     */
    class musicFinder implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return (name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith(".flac") || dir.isDirectory());
        }
    }


    /*getter for SD_LOCATION*/
    public String getSD_LOCATION() {
        return SD_LOCATION;
    }

    //Method for checking if songList already has songID stored in it.
    public boolean trackInSongList(ArrayList songsList, int songID) {
        return songsList.contains(songID);
    }


    /*getter for the path of the audio file on the SD Card*/
    public String getPath(File track) {
        return track.getAbsolutePath();
    }

    /*
    The list of songs available on the SD card will be a list of HashMaps mapping:
        key - a unique song ID stored as an Integer
        value - a array list of type string that will consist of:
            [0] - Song Path
            [1] - Song Name
            [2] - Artist Name
            [3] - Album Name
            [5] - Genre
            [6 or higher] - Trait tags
     */

    public ArrayList<Song> makeSongsList() throws Exception {
        try {
            File sdCard = new File(SD_LOCATION);
            if (sdCard.listFiles().length > 0) {
                for (File file : sdCard.listFiles(new musicFinder())) {
                    //sets up String[] of track info
                    //System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()){
                        System.out.println("AYYYYYY\n\n\n\n");
                        System.out.println(file.getAbsoluteFile());
                        makeSongsList(file.getAbsolutePath());
                    }
                    else if (file.getAbsolutePath().toLowerCase().endsWith(".mp3")  || file.getAbsolutePath().toLowerCase().endsWith(".flac")){
                        Song so = new Song(file.getAbsolutePath());
                        if (so == null){
                            System.out.println("so == null!\n");
                        }
                        //String thetitle = so.get_id3().getTitle();
                        //System.out.println(thetitle);
                        //System.out.println(so.get_id3().getArtist());
                        //System.out.println(so.get_id3().getAlbum());
                        _songsList.add(so);
                    }
                }
            }
            //System.out.println(_songsList.size());
        } catch (Exception e) {
            Log.e("lmm", "err setting datasource", e);
        }
        return _songsList;
    }
    private ArrayList<Song> makeSongsList(String folder) throws Exception {
        try {
            File sdCard = new File(folder);
            if (sdCard.listFiles().length > 0) {
                for (File file : sdCard.listFiles(new musicFinder())) {
                    if (file.isDirectory()){
                        makeSongsList(file.getAbsolutePath());
                    }
                    else if (file.getAbsolutePath().toLowerCase().endsWith(".mp3") || file.getAbsolutePath().toLowerCase().endsWith(".flac")){
                        Song so = new Song(file.getAbsolutePath());
                        if (so == null){
                            System.out.println("so == null!\n");
                        }
                        //String thetitle = so.get_id3().getTitle();
                        //System.out.println(thetitle);
                        //System.out.println(so.get_id3().getArtist());
                        //System.out.println(so.get_id3().getAlbum());
                        _songsList.add(so);
                    }
                }
            }
            //System.out.println(_songsList.size());
        } catch (Exception e) {
            Log.e("lmm", "err setting datasource", e);
        }
        return _songsList;
    }
    public ArrayList<String> makeSongNames () {
        String songName;
        String artistName;
        String albumName;
        String mergedName;
        for (Song s : _songsList) {
            songName = s.get_id3().getTitle();
            artistName = s.get_id3().getArtist();
            albumName = s.get_id3().getAlbum();
            if (artistName == null) {
                artistName = "Unknown Artist";
            }
            if (albumName == null) {
                albumName = "Unknown Album";
            }
            mergedName = songName + " - " + artistName + " - " + albumName;
            if (songName == null) {
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
        return songArray;
    }
    public ArrayList<String> makeSongNames (ArrayList<Song> songs) {
        String songName;
        String artistName;
        String albumName;
        String mergedName;
        ArrayList<String> names = new ArrayList<>();
        for (Song s : songs) {
            songName = s.get_id3().getTitle();
            artistName = s.get_id3().getArtist();
            albumName = s.get_id3().getAlbum();
            if (artistName == null) {
                artistName = "Unknown Artist";
            }
            if (albumName == null) {
                albumName = "Unknown Album";
            }
            mergedName = songName + " - " + artistName + " - " + albumName;
            if (songName == null) {
                File f = new File(s.get_audioFilePath());
                mergedName = f.getName();
            }
            System.out.println(mergedName);
            //songArray[i] = (mergedName);
            names.add(mergedName);
            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sampleArray);
            //ListView libView = (ListView) findViewById(R.id.librarylist);
            //libView.setAdapter(arrayAdapter);
            //arrayAdapter.notifyDataSetChanged();
            //i++;
        }
        System.out.println("we're in LMM and the songarray namemaker and here's the list");
        for (String s : names) {
            System.out.println(s);
        }
        System.out.println("that was the list yay!\nand size is:  "+ names.size());
        return names;


    }
}