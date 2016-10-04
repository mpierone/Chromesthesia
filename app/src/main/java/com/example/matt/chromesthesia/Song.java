package com.example.matt.chromesthesia;

/**
 * Created by Dave on 10/3/2016.
 */
public class Song {
    private long identification;
    private String title;
    private String artist;

    public Song(long sngIDnum, String sngtitle, String sngartist) {
        identification = sngIDnum;
        title = sngtitle;
        artist = sngartist;
    }

    public getID () {return identification;}
    public gettitle() {return title;}
    public getartist() {return artist;}

}
