package com.example.matt.chromesthesia;

import com.example.matt.chromesthesia.playlistDev.ID3;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Dave on 10/3/2016.
 */
public class Song {
    private HashMap<String, ID3> _song;
    private String _identification;
    private String _audioFilePath;
    private localMusicManager _localManager;

    /*
  The list of songs available on the SD card will be a list of HashMaps mapping:
      key - a unique song ID stored as an Integer
      value - a array list of type string that will consist of:
          [0] - Song Path
          [1] - Song Title
          [2] - Artist Name
          [3] - Album Name
          [5] - Genre
          [6 or higher] - Trait tags
   */
    public Song(String audioFilePath, ID3 id) throws Exception {
        //unique song identifier:
        _localManager = new localMusicManager(); //used in generateLocalSongID, might be necessary later on
        _identification = generateLocalSongID();

        //song attributes:
        _audioFilePath = audioFilePath;

        //storing song identification
        _song = new HashMap<>();
        _song.put(_identification, id);
    }


    /*Generates a unique song ID for the track.
        MUST:
         - Check songsList for a repeated entry
       Once we begin implementing the server, we want this to be a String so it's compatible with
       the METADATA_KEY_MEDIA_ID key for identifying content. See: "MediaMetaDataCompat" in Android
       Developer Documentation

       Note: int max refers to the highest number that will be the song ID.
       8000 is an estimate I found online for how many songs a 32GB SD card could store
       Thus, each song would be identified by <a number from 1 to 8000>

     */

    public String generateLocalSongID() throws Exception {
        int min = 1;
        int max = 8000;
        Long songID;
        int random = ThreadLocalRandom.current().nextInt(min, max+1);
        //implies that if generateLocalSongID returns 0, then the song is already in the database
        if (!_localManager.trackInSongList(_localManager.getSongsList(),random)){
            songID = new Long(random);
        }
        else{ songID = Long.valueOf(0);}
            return songID.toString();
    }

}
