package com.example.matt.chromesthesia.playlistDev;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Isabelle on 10/4/2016.
 *
 * This class reads the files from the SD card.
 * Detects any audio files
 */


public class localMusicManager {
    final String SD_LOCATION = new String("/sdcard/");
    private ArrayList<HashMap<Integer, String[]>> songsList = new ArrayList<HashMap<Integer, String[]>>();

    public localMusicManager() {
        //constructor
    }

    /*
    Detects audio files with extensions: .mp3, .mp4a, and .wav
     */
    class musicFinder implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3") || name.endsWith(".mp4a") || name.endsWith(".MP4A") || name.endsWith(".wav") || name.endsWith(".WAV"));
        }


    }


    //Method for checking if songList already has songID stored in it.
    public boolean trackInSongList(ArrayList songsList, int songID){
        return songsList.contains(songID);
    }

    /*Generates a unique song ID for the track.
        MUST:
         - Check songsList for a repeated entry
       Once we begin implementing the server, we want this to be a String so it's compatible with
       the METADATA_KEY_MEDIA_ID key for identifying content. See: "MediaMetaDataCompat" in Android
       Developer Documentation

       Note: int max refers to the highest number that will be concatenated to the song name to create the song ID
       8000 is an estimate I found online for how many songs a 32GB SD card could store
       Thus, each song would be identified by <a number from 1 to 8000>

     */
    public Integer generateLocalSongID() {
        int min = 1;
        int max = 8000;
        int random = ThreadLocalRandom.current().nextInt(min, max+1);
        //implies that if generateLocalSongID returns 0, then the song is already in the database
        if (!trackInSongList(songsList,random)){
            Integer songID = new Integer(random);
            return songID;
            }
        else return 0;
    }

    public String getFileType(File dir, String name){
        if (name.endsWith(".mp3") || name.endsWith(".MP3") ||name.endsWith(".wav") || name.endsWith(".WAV")){
            return name.substring(name.length()-4, name.length());
        }
        else return name.substring(name.length()-5, name.length());
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

    public ArrayList<HashMap<Integer, String[]>> getSongsList() {


        File sdCard = new File(SD_LOCATION);
        if (sdCard.listFiles(new musicFinder()).length > 0) {
            for (File file : sdCard.listFiles(new musicFinder())) {
                //sets up String[] of track info

                String[] trackInfo = {file.getPath(), file.getName().substring(0,file.getName().indexOf(getFileType(sdCard,file.getName()))),"Artist Not Available","Album Not Available","Genre Not Available"};

                HashMap<Integer, String[]> song = new HashMap<Integer, String[]>();
                song.put(generateLocalSongID(), trackInfo);
                songsList.add(song);



            }
        }
        return songsList;
    }
}
