package com.example.matt.chromesthesia;

import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import org.junit.Test;

import java.util.ArrayList;

public class localMusicManagerTests {

    /* Should print to console:
 Song ID: null
 Title: rumine
 By: Artist Not Available
 Album: Album Not Available
 Genre: Genre Not Available

 Song ID: null
 Title: sutphinboulevard
 By: Artist Not Available
 Album: Album Not Available
 Genre: Genre Not Available

        For the sake of testing we might make the filepath for the localMusicManager to rummage through
        the raw directory in res.
    */

    @Test
    public void getSongList() {
        localMusicManager exampleMgr = new localMusicManager();
        ArrayList<Song> songs = exampleMgr.getSongsList();
        for (int i=0; i< songs.size(); i++){
            songs.get(i).printSongInfo();
        }
    }

}