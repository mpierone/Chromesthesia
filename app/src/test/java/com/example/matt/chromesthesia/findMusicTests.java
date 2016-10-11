package com.example.matt.chromesthesia;

import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

/**
 * Created by Isabelle on 10/11/2016.
 */
@RunWith(JUnit4.class)
public class findMusicTests {

    @Test()
    public void getLibrary() {
        localMusicManager lmm = new localMusicManager();
        ArrayList<Song> songs = null;
        try {
            songs = lmm.makeSongsList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Song s : songs) {
            System.out.println(
            s.get_id3().getTitle() +
                    s.get_id3().getArtist() +
                    s.get_id3().getAlbum()
            );
        }
    }
}
