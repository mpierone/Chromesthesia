package com.example.matt.chromesthesia;

<<<<<<<.merge_file_a07636
import android.media.MediaMetadataRetriever;

import com.example.matt.chromesthesia.playlistDev.ID3;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
        =======
        import com.example.matt.chromesthesia.playlistDev.localMusicManager;
        import com.example.matt.chromesthesia.playlistDev.mp3Parser;

        import org.junit.Test;
        >>>>>>>.merge_file_a01364

/**
 * Created by Isabelle on 10/7/2016.
 */
        <<<<<<<.merge_file_a07636
@RunWith(MockitoJUnitRunner.class)
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
        for (Song s : lmm.makeSongsList()) {
            System.out.println(
                    "Song ID#:" + s.get_identification());
            printID3(s.get_audioFilePath());
            =======

            public class LocalLibraryTests {
                @Test
                public void getAllSongs() throws Exception {
                    localMusicManager lmm = new localMusicManager();
                    mp3Parser mp = new mp3Parser();
                    for (Song s : lmm.makeSongsList()) {
                        System.out.println(
                                "Song ID#:" + s.get_identification());
                        mp.printID3(s.get_audioFilePath());
                        >>>>>>>.merge_file_a01364
            System.out.println(
                    "\n"
            )
            ;
        }
    }
}
