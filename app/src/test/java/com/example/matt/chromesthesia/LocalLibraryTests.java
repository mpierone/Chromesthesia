package com.example.matt.chromesthesia;

import com.example.matt.chromesthesia.playlistDev.localMusicManager;
import com.example.matt.chromesthesia.playlistDev.mp3Parser;

import org.junit.Test;

/**
 * Created by Isabelle on 10/7/2016.
 */

public class LocalLibraryTests {
    @Test
    public void getAllSongs() throws Exception {
        localMusicManager lmm = new localMusicManager();
        mp3Parser mp = new mp3Parser();
        for (Song s : lmm.makeSongsList()) {
            System.out.println(
                    "Song ID#:" + s.get_identification());
            mp.printID3(s.get_audioFilePath());
            System.out.println(
                    "\n"
            )
            ;
        }
    }
}
