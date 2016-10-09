package com.example.matt.chromesthesia.playlistDev;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

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



    final String SD_LOCATION = new String("/SDCARD/"); //change back to "/sdcard/" after unit testing
    private ArrayList<Song> _songsList;
    public localMusicManager() {
        //constructor
        _songsList = new ArrayList<>();
    }

    /*
    Detects audio files with extensions: .mp3, .mp4a, and .wav
     */
    class musicFinder implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }


    /*getter for SD_LOCATION*/
    public String getSD_LOCATION(){
        return SD_LOCATION;
    }

    //Method for checking if songList already has songID stored in it.
    public boolean trackInSongList(ArrayList songsList, int songID){
        return songsList.contains(songID);
    }



    /*getter for the path of the audio file on the SD Card*/
    public String getPath(File track){
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
        File sdCard = new File(SD_LOCATION);
        if (sdCard.listFiles(new musicFinder()).length > 0) {
            for (File file : sdCard.listFiles(new musicFinder())) {
                //sets up String[] of track info

                Song so = new Song(file.getAbsolutePath());
                _songsList.add(so);
            }
        }
        return _songsList;
    }



}