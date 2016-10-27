package com.example.matt.chromesthesia.playlistDev;

import android.os.Environment;
import android.util.Log;

import com.example.matt.chromesthesia.Song;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Isabelle on 10/25/2016.
 * Finds playlists saved by user.
 *
 * If you don't have an SD card, it won't be able to write your text documents to anything.
 *
 * This is why I added the openFileInput() to the get Playlistlist method
 * and openFileOutput() to the save feature. These methods are used to find
 * internal storage. All files saved to internal storage from an application
 * are private to that application by default
 * */


public class PlaylistManager {

    String SD_LOCATION = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
    File sdlocation = new File(SD_LOCATION + "/Download/");
    boolean deviceHasSDCard;
    //private ArrayList<Playlist> playlistList;

    public PlaylistManager(){
        //constructor
        if (isExternalStorageReadable() == true && isExternalStorageWritable() ==true){
            String SD_LOCATION = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
            File sdlocation = new File(SD_LOCATION + "/Download/");
            deviceHasSDCard = true;
        }
        else{
            deviceHasSDCard = false;
        }

    }


    /*get Arraylist of all playlists found on sd card. We need to call this in the PlaylistSelectionScreen to be able to see our playlists listed there.*/
    public ArrayList<Playlist> getPlaylistList(String folder) throws Exception{
        ArrayList<Playlist> playlistList = new ArrayList<>();
        try {
            if (deviceHasSDCard == true) {
                File playDir = getPlaylistStorageDirectory();
                if (playDir.listFiles().length > 0) {
                    for (File file : playDir.listFiles()) {
                        //sets up String[] of track info
                        //System.out.println(file.getAbsolutePath());
                        if (file.isDirectory()) {
                            System.out.println("keep on lookin', subfolders will eventually be how we group similar playlists together\n\n\n\n");
                            System.out.println(file.getAbsoluteFile());
                            getPlaylistList(file.getAbsolutePath());
                        } else if (file.getAbsolutePath().toLowerCase().endsWith(".txt")) {
                            //String thetitle = so.get_id3().getTitle();
                            //System.out.println(thetitle);
                            //System.out.println(so.get_id3().getArtist());
                            //System.out.println(so.get_id3().getAlbum());
                            String playlistName = file.getName().replace(".txt", "");
                            Playlist p = new Playlist(playlistName);
                            playlistList.add(p);
                            System.out.println("Added " + p._playlistName + " to the playlist ArrayList.");
                        }
                    }
                }
                //System.out.println(_songsList.size());
            }
        }catch(Exception e){
                Log.e("lmm", "err setting datasource", e);
            }
        return playlistList;
    }



    /**using this in saving the playlist txt files to the sd card? */
    public File getPlaylistStorageDirectory(){
        //directory creation
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "playlists");
            if (!file.exists()) {
                file.mkdirs();
                System.out.println("Couldn't find the folder containing your playlists in Downloads");
                System.out.println("Created new playlists folder in Downloads");
            }
        return file;
    }



    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /*Saving a playlist into a string array into a text file
    * MUST TEST FOR: At creation of a playlist savePlaylist() txt doc is...
    * _playlistName.txt:
    *   empty
    *
    * If I use setPlaylistFiles and save again, I need to make sure it clears the contents of _playlistname.txt
    * and replaces it with the new arraylist of audio files to find
    * */
    public void savePlaylist(Playlist playlist) throws FileNotFoundException {
        File _playlistFile = playlist._playlistTxtDoc;
        ArrayList<Song> _playlistSongs = playlist._playlistSongs;
        File plStorageDir = getPlaylistStorageDirectory();
        if (deviceHasSDCard == true) {
            try {
                FileWriter writer = new FileWriter(_playlistFile);
                BufferedWriter bw = new BufferedWriter(writer);
                //Go to directory and look for files and list them
                if (plStorageDir.listFiles().length > 0) {
                    //Once you find the directory, check list of files for:

                    for (File file : plStorageDir.listFiles()) {
                        //folders within the directory, and search those
                        if (file.isDirectory()) {
                            savePlaylist(file.getAbsolutePath(), playlist);
                            //code for finding it in a subfolder
                        }
                        //if the current file is the playlist's txt file and overwrite it with new information stored in playlistSongs array list
                        if (file.getAbsolutePath().toLowerCase() == _playlistFile.getAbsolutePath().toLowerCase()) {
                            file.delete();
                            _playlistFile = new File (getPlaylistStorageDirectory(), playlist._playlistFileName);
                            if (_playlistSongs != null) {
                                for (String s : playlist.getPlaylistFilenamesArray()) {
                                    bw.append(s);
                                    bw.append("\n");
                                    System.out.println("Added " + s);
                                }
                            } else {
                                bw.append("empty");
                                System.out.println("This is an empty playlist. No songs to save to text file.");
                            }
                        }
                        //if the file exists in the folder; if not, then create a new file and write the playlistSongs array list contents to it line by line
                        else if (!_playlistFile.exists()) {
                            _playlistFile = new File(getPlaylistStorageDirectory(),playlist._playlistFileName);
                            if (_playlistSongs != null) {
                                for (String s : playlist.getPlaylistFilenamesArray()) {
                                    bw.append(s);
                                    bw.append("\n");
                                    System.out.println("Added " + s);
                                }
                            } else {
                                bw.append("empty");
                                System.out.println("This is an empty playlist. No songs to save to text file.");
                            }
                            bw.close();
                            writer.close();
                        }
                    }

                }
                //if the folder was empty to begin with!
                else if (plStorageDir.listFiles().length == 0){
                    if (_playlistSongs != null) {
                        for (String s : playlist.getPlaylistFilenamesArray()) {
                            bw.append(s);
                            bw.append("\n");
                            System.out.println("Added " + s);
                        }
                    } else {
                        bw.append("empty");
                        System.out.println("This is an empty playlist. No songs to save to text file.");
                    }
                    bw.close();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(playlist.getPlaylistName() + " was saved as " + playlist.getNameOfTextFile() + " in " + getPlaylistStorageDirectory() + ".");
    }


    //overloading savePlaylist for recursion to find subfolders
    public void savePlaylist(String directory, Playlist playlist) throws FileNotFoundException {
        File _playlistFile = new File (getPlaylistStorageDirectory(),playlist._playlistFileName);
        ArrayList<Song> _playlistSongs = playlist._playlistSongs;
        File plStorageDir = new File (directory);
        if (deviceHasSDCard == true) {
            try {
                FileWriter writer = new FileWriter(_playlistFile);
                BufferedWriter bw = new BufferedWriter(writer);
                //Go to directory and look for files and list them
                if (plStorageDir.listFiles().length > 0) {
                    //Once you find the directory, check list of files for:

                    for (File file : plStorageDir.listFiles()) {
                        //folders within the directory, and search those
                        if (file.isDirectory()) {
                            savePlaylist(file.getAbsolutePath(), playlist);
                            //code for finding it in a subfolder
                        }
                        //if the current file is the playlist's txt file and overwrite it with new information stored in playlistSongs array list
                        if (file.getAbsolutePath().toLowerCase() == _playlistFile.getAbsolutePath().toLowerCase()) {
                            file.delete();
                            _playlistFile = new File (getPlaylistStorageDirectory(), playlist._playlistFileName);
                            if (_playlistSongs != null) {
                                for (String s : playlist.getPlaylistFilenamesArray()) {
                                    bw.append(s);
                                    bw.append("\n");
                                    System.out.println("Added " + s);
                                }
                            } else {
                                bw.append("empty");
                                System.out.println("This is an empty playlist. No songs to save to text file.");
                            }
                        }
                        //if the file exists in the folder; if not, then create a new file and write the playlistSongs array list contents to it line by line
                        else if (!_playlistFile.exists()) {
                            _playlistFile = new File(getPlaylistStorageDirectory(),playlist._playlistFileName);
                            if (_playlistSongs != null) {
                                for (String s : playlist.getPlaylistFilenamesArray()) {
                                    bw.append(s);
                                    bw.append("\n");
                                    System.out.println("Added " + s);
                                }
                            } else {
                                bw.append("empty");
                                System.out.println("This is an empty playlist. No songs to save to text file.");
                            }
                            bw.close();
                            writer.close();
                        }
                    }

                }
                //if the folder was empty to begin with!
                else if (plStorageDir.listFiles().length == 0){
                    if (_playlistSongs != null) {
                        for (String s : playlist.getPlaylistFilenamesArray()) {
                            bw.append(s);
                            bw.append("\n");
                            System.out.println("Added " + s);
                        }
                    } else {
                        bw.append("empty");
                        System.out.println("This is an empty playlist. No songs to save to text file.");
                    }
                    bw.close();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(playlist.getPlaylistName() + " was saved as " + playlist.getNameOfTextFile() + " in " + getPlaylistStorageDirectory() + ".");
    }


    /*Loading the playlist from text file back into an ArrayList<String>*/
    public ArrayList<String> loadPlaylist(File txtfilename) throws FileNotFoundException {
        ArrayList<String> storedPlaylist = new ArrayList<>();
        Scanner scan = new Scanner(txtfilename);
        while (scan.hasNext()) {
            storedPlaylist.add(scan.next());
        }
        return storedPlaylist;
    }




}
