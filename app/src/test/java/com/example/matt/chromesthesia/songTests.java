package com.example.matt.chromesthesia;

import org.junit.Test;
public class songTests {

    @Test
    public void getAllInfoTest() {
        Song exampleSong = new Song("C:\\Users\\Isabelle\\Chromesthesia\\app\\src\\main\\res\\raw\\rumine.mp3", "R U Mine?", "Arctic Monkeys", "AM", "Alternative");
        exampleSong.printSongInfo();
    }

}