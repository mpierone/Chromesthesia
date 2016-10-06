package com.example.matt.chromesthesia;

import org.junit.Test;
public class songTests {

    /* Should print to console:
        Song ID : null (won't allocate a song ID because the localMusicManager needs to be run on something with an SD Card)
        Title: R U Mine?
        By: Arctic Monkeys
        Album: AM
        Genre: Alternative

        For the sake of testing we might make the filepath for the localMusicManager to rummage through
        the raw directory in res.
    */

    @Test
    public void getAllInfoTest() {
        Song exampleSong = new Song("C:\\Users\\Isabelle\\Chromesthesia\\app\\src\\main\\res\\raw\\rumine.mp3", "R U Mine?", "Arctic Monkeys", "AM", "Alternative");
        exampleSong.printSongInfo();
    }

}