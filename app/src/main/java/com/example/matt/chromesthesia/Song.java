package com.example.matt.chromesthesia;

import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Dave on 10/3/2016.
 */
public class Song {
    private HashMap<Long, String[]> _song;
    private String[] _trackInfo;
    private long _identification;
    private String _title;
    private String _artist;
    private String _audioFilePath;
    private String _album;
    private String _genre;
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
    public Song( String audioFilePath, String sngtitle, String sngartist,  String album, String genre) {
        _localManager = new localMusicManager();
        _identification = generateLocalSongID();
        _title = sngtitle;
        _artist = sngartist;
        _audioFilePath = audioFilePath;
        _album = album;
        _genre = genre;
        _trackInfo = new String[]{_audioFilePath, _title, _artist, _album, _genre};
        _song = new HashMap<Long, String[]>();
        _song.put(_identification, _trackInfo);
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
    public Long generateLocalSongID() {

        int min = 1;
        int max = 8000;
        int random = ThreadLocalRandom.current().nextInt(min, max+1);
        //implies that if generateLocalSongID returns 0, then the song is already in the database
        if (!_localManager.trackInSongList(_localManager.getSongsList(),random)){
            Long songID = new Long(random);
            return songID;
        }
        else return Long.valueOf(0);
    }

    public Long getID() {
        return _identification;
    }

    public String gettitle() {
        return title;
    }

    public String getartist() {
        return artist;
    }

}
