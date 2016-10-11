package com.example.matt.chromesthesia.playlistDev;

import com.example.matt.chromesthesia.Song;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by Isabelle on 10/4/2016.
 * <p>
 * This class reads the files from the SD card.
 * Detects any audio files
 */


public class localMusicManager {


    final String SD_LOCATION = "C:\\Users\\Isabelle\\Chromesthesia\\app\\src\\main\\res\\raw";
    static ArrayList<Song> _songsList;

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


    public ArrayList<Song> makeSongsList() {
        File sdCard = new File(SD_LOCATION);
        if (sdCard.listFiles(new musicFinder()).length > 0) {
            for (File file : sdCard.listFiles(new musicFinder())) {
                //sets up String[] of track info
                Song so = null;
                try {
                    so = new Song(file.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                _songsList.add(so);
            }
        }
        else{
            try {
                _songsList.add(new Song("file///raw/"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _songsList;
    }


}
