package com.example.matt.chromesthesia;

import android.media.MediaMetadataRetriever;

import com.example.matt.chromesthesia.playlistDev.ID3;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import java.util.ArrayList;

/**
 * Created by Isabelle on 10/7/2016.
 */
@RunWith(JUnit4.class)
public class LocalLibraryTests {

    @Mock
    MediaMetadataRetriever mm;

    public ID3 parseMP3(String audioPath) {
        mm = new MediaMetadataRetriever();
        mm.setDataSource(audioPath);
        return new ID3(new String(mm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)),
                new String(mm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)),
                new String(mm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)),
                new String(mm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR)),
                new String(mm.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE))
        );
    }


    public void printID3(String audioPath) throws Exception {
        ID3 test = parseMP3(audioPath);
        System.out.println(
                "Album: " + test.getAlbum() + "\n" +
                        "Genre: " + test.getGenre() + "\n" +
                        "Year: " + test.getYear() + "\n" +
                        "Artist: " + test.getArtist() + "\n" +
                        "Song Title: " + test.getTitle()
        );
    }


    @Test
    public void getAllSongs() throws Exception {
        localMusicManager lmm = new localMusicManager();
        for (Song s : lmm.makeSongsList()){
            System.out.println(
                    "Song ID#:" + s.get_identification());
            printID3(s.get_audioFilePath());
            System.out.println(
                    "\n"
            )
            ;
        }
    }

    @Test
    public void saveAPlaylist() throws Exception{
        localMusicManager lmm  = new localMusicManager();
        ArrayList<Song> testerPlaylist = new ArrayList<>();
        testerPlaylist.add(new Song("C:\\Users\\Isabelle\\Chromesthesia\\app\\src\\main\\res\\raw\\rumine.mp3"));
        testerPlaylist.add(new Song("C:\\Users\\Isabelle\\Chromesthesia\\app\\src\\main\\res\\raw\\sutphinboulevard.mp3"));
        lmm.savePlaylist("newplaylist.txt", testerPlaylist);
    }
}