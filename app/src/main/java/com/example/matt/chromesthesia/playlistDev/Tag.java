package com.example.matt.chromesthesia.playlistDev;

import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by Isabelle on 10/6/2016.
 *
 * Parses ID3 tags from mp3's to get artist, album, title, year, genre, and comment info
 * Utilized this tutorial courtesy of Paul Davis, Lead Software Development Engineer at Rhapsody International Inc.
 * http://willcode4beer.com/parsing.jsp?set=mp3ID3
 */

public class Tag {
    private String title;
    private String artist;
    private String album;
    private String year;
    private String comment;
    private byte genre;
    private int tagSize = 128;
    public Tag(){
        //constructor
    }

    public void setTitle(String bTitle){
        title = bTitle;
    }

    public void setArtist(String bArtist){
        artist = bArtist;
    }

    public void setAlbum(String bAlbum){
        album = bAlbum;
    }

    public void setYear(String bYear){
        year = bYear;
    }

    public void setComment(String bComment){
        comment = bComment;
    }

    public void setGenre (byte bGenre){
        genre = bGenre;
    }

    public Tag populateTag(ByteBuffer buf){
        byte[] tag = new byte[3];
        byte[] tagTitle = new byte[30];
        byte[] tagArtist = new byte[30];
        byte[] tagAlbum = new byte[30];
        byte[] tagYear = new byte[4];
        byte[] tagComment = new byte[30];
        byte[] tagGenre = new byte[1];
        buf.get(tag).get(tagTitle).get(tagArtist).get(tagAlbum).get(tagYear).get(tagComment).get(tagGenre);
        if(!"TAG".equals(new String(tag))){
            throw new IllegalArgumentException(
                    "No ID3 tag data available."
            );
        }
        Tag id3 = new Tag();
        id3.setTitle(new String(tagTitle).trim());
        id3.setArtist(new String(tagArtist).trim());
        id3.setAlbum(new String(tagAlbum).trim());
        id3.setYear(new String(tagYear).trim());
        id3.setComment(new String(tagComment).trim());
        id3.setGenre(tagGenre[0]);
        return id3;

    }

    public Tag readTag(InputStream input) throws Exception{
        byte[] tagBytes = new byte[tagSize];
        int pos = 0;
        for (int i=1; (i = (input.read())) >= 0;){
            tagBytes[pos++] = (byte)i;
                if(pos==tagSize){
                    pos=0;
                }
        }
        ByteBuffer buf = ByteBuffer.allocate(tagSize);
        buf.put(tagBytes,pos,tagSize-pos);
        buf.put(tagBytes,0,pos);
        buf.rewind();
        return populateTag(buf);
    }
}
