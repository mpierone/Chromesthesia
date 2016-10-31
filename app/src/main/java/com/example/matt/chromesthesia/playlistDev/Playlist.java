package com.example.matt.chromesthesia.playlistDev;

import com.example.matt.chromesthesia.Song;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Isabelle on 10/25/2016.
 *
 * Playlist object
 */

public class Playlist {
    String _playlistName;
    public ArrayList<Song> _playlistSongs;
    ArrayList<Song> defaultEmptyPlaylistFiles;
    public ArrayList<String> stringFilenames;
    String _playlistFileName;
    File _playlistTxtDoc;
    boolean isSavedOnExternalStorage;

    public Playlist(String name){
        //constructor
        _playlistName = name;
        _playlistFileName = _playlistName + ".txt";

        PlaylistManager pm = new PlaylistManager();
        _playlistTxtDoc = new File (pm.getPlaylistStorageDirectory(), _playlistFileName);

        stringFilenames = new ArrayList<>();
        _playlistSongs = new ArrayList<>();


        if(stringFilenames.size()>0) {
            for (String s : stringFilenames) {
                try {
                    Song so = new Song(s);
                    _playlistSongs.add(so);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

   /* public void savePlaylist(){
    PlaylistManager pm = new PlaylistManager();
        if (pm.deviceHasSDCard == true) {
            try {
                pm.savePlaylist(this);
            } catch (FileNotFoundException e) {
                System.out.println("Couldn't find the storage directory to save the txt document.");
                e.printStackTrace();
            }
            isSavedOnExternalStorage = true;
        }else{
            isSavedOnExternalStorage = false;
            System.out.println("No SD Card found. Trying internal storage.");
        }
    }*/

    public void setPlaylistName(String playlistName){
        _playlistName = playlistName;
    }

    public String getNameOfTextFile(){
        return _playlistTxtDoc.getName();
    }

    public String getPlaylistName(){
        return _playlistName;
    }

    public ArrayList<Song> getPlaylistAudioFiles(){
        return _playlistSongs;
    }

    public ArrayList<String> getFilenamesArray(){return stringFilenames;}

    public ArrayList<String> extractFilenamesFromSongs(){
        ArrayList<String> filenames = new ArrayList<>();
        for (Song s : _playlistSongs){
            filenames.add(s.get_audioFilePath());
        }
        return filenames;
    }


    /*For use in playlist creation*/


    /*Loading the playlist from text file back into an ArrayList<String>*/
    public ArrayList<String> loadPlaylist() throws FileNotFoundException {
        PlaylistManager pm = new PlaylistManager();
        File plStorageDir = pm.getPlaylistStorageDirectory();


        try(BufferedReader br = new BufferedReader(new FileReader(plStorageDir + "/" + _playlistFileName))){
            for (String line; (line = br.readLine()) != null;){
             stringFilenames.add(line);
                System.out.println("Added " + line + " to " + _playlistName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        String[] data = new String[stringFilenames.size()];

        for (int i = 0; i < stringFilenames.size() - 1; i++) {
            data[i] = stringFilenames.get(i);
        }
        FileInputStream input = null;
        try {
            //it doesn't know what the playlist's filename is
            input = new FileInputStream(plStorageDir + "/" + _playlistFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                for (int i = 0; i < data.length - 1; i++) {
                    stringFilenames.add(String.valueOf(input.read(data[i].getBytes())));
                    System.out.println(data[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        System.out.println("Loaded playlist " + _playlistName + " from " + _playlistFileName + " with size " + stringFilenames.size());

        return stringFilenames;
    }



}
