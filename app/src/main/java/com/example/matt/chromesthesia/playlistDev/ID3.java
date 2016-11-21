package com.example.matt.chromesthesia.playlistDev;

/**
 * Created by Isabelle on 10/6/2016.
 *
 * Stores ID3 tags parsed using TagParser class
 *
 */

public class ID3 {
    private String _title;
    private String _artist;
    private String _album;
    private String _year;
    private String _genre;

    public ID3(String title, String artist, String album, String year, String genre){
        //constructor
        _title = title;
        _artist = artist;
        _album = album;
        _year = year;
        _genre = genre;

    }

    public String getTitle(){
        return _title;
    }

    public String getArtist(){
        return _artist;
    }

    public String getAlbum(){
        return _album;
    }

    public String getYear(){
        return _year;
    }


    public String getGenre (){
        return _genre;
    }
 //Setters
    public void setTitle(String bTitle){
        _title = bTitle;
    }

    public void setArtist(String bArtist){
        _artist = bArtist;
    }

    public void setAlbum(String bAlbum){
        _album = bAlbum;
    }

    public void setYear(String bYear){
        _year = bYear;
    }

    public void setGenre (String bGenre){
        _genre = bGenre;
    }


}
