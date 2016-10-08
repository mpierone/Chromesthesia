package com.example.matt.chromesthesia;

import com.example.matt.chromesthesia.playlistDev.ID3;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;
import com.example.matt.chromesthesia.playlistDev.mp3Parser;
import java.math.BigInteger;
import java.util.HashMap;
import java.lang.Long;
/**
 * Created by Dave on 10/3/2016.
 */
public class Song {
    private HashMap<BigInteger, ID3> _song;
    private BigInteger _identification;
    private String _audioFilePath;
    private ID3 _id3;
    private localMusicManager _localManager;

    /*
    Songs consist of a unique identification number and an ID3 object storing metadata (artist, album, etc)
   */
    public Song(String audioFilePath, long id) throws Exception {
        //unique song identifier:
        _localManager = new localMusicManager(); //used in generateLocalSongID, might be necessary later on
        _identification = BigInteger.valueOf(id);


        //song attributes:
        _audioFilePath = audioFilePath;


        _id3 = new mp3Parser().parseMP3(_audioFilePath);

        //storing song identification
        _song = new HashMap<>();
        _song.put(_identification, _id3);

    }

    //getters:

    public ID3 get_id3(){
        //should work because a song object should only have one key in its hashmap
        return _song.get(_song.keySet());
    }

    public String get_identification(){
        return String.valueOf(_identification);
    }
    public BigInteger getid() {return _identification;}
    public String get_audioFilePath(){
        return _audioFilePath;
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
    /*
    public String generateLocalSongID() throws Exception {
        int min = 1;
        int max = 8000;
        Long songID;
        int random = ThreadLocalRandom.current().nextInt(min, max+1);
        //implies that if generateLocalSongID returns 0, then the song is already in the database
        if (!_localManager.trackInSongList(_localManager.makeSongsList(),random)){
            songID = new Long(random);
        }
        else{ songID = Long.valueOf(0);}
            return songID.toString();
    }

    For testing purposes because the random generator was giving me errors:
*/
    public long tempID(){
        return -1;
    }

}
