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
 */

public class PlaylistManager {
    final String SD_LOCATION = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
    File sdlocation = new File(SD_LOCATION + "/Download/");
    protected ArrayList<Playlist> playlistList;

    /*get Arraylist of all playlists found on sd card*/
    public ArrayList<Playlist> getPlaylistList(String folder) throws Exception{
        try {
            File sdCard = new File(SD_LOCATION);
            if (sdCard.listFiles().length > 0) {
                for (File file : sdCard.listFiles()) {
                    //sets up String[] of track info
                    //System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        System.out.println("keep on lookin'\n\n\n\n");
                        System.out.println(file.getAbsoluteFile());
                        getPlaylistList(file.getAbsolutePath());
                    } else if (file.getAbsolutePath().toLowerCase().endsWith(".txt")) {
                        Playlist p = new Playlist(file.getName().subSequence(0,file.getName().length()-4).toString(), file.getAbsoluteFile());
                        if (p == null) {
                            System.out.println("p == null!\n");
                        }
                        //String thetitle = so.get_id3().getTitle();
                        //System.out.println(thetitle);
                        //System.out.println(so.get_id3().getArtist());
                        //System.out.println(so.get_id3().getAlbum());
                        playlistList.add(p);
                    }
                }
            }
            //System.out.println(_songsList.size());
        } catch (Exception e) {
            Log.e("lmm", "err setting datasource", e);
        }
        return playlistList;
    }



    /**using this in saving the playlist txt files to the sd card*/
    public File getPlaylistStorageDirectory(){
        //directory creation
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), ".txt");
        if (!file.mkdirs()){
            System.out.println("Playlist Directory not created");
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
    public void savePlaylist(String directory, Playlist playlist) throws FileNotFoundException {
        File _playlistFile = playlist._playlistFile;
        ArrayList<Song> _playlistSongs = playlist._playlistSongs;
        File sdCard = new File(directory);
        try {
            if (sdCard.listFiles().length > 0) {
                for (File file : sdCard.listFiles()) {
                    if (file.isDirectory()) {
                        savePlaylist(file.getName(), playlist);
                        //code for finding it in a subfolder
                    }
                    if (file.getAbsolutePath().toLowerCase() == playlist._playlistFile.getAbsolutePath().toLowerCase()) {
                        //code for editing an existing playlist
                    } else if (!_playlistFile.exists()) {
                        _playlistFile.createNewFile();
                        FileWriter writer = new FileWriter(_playlistFile);
                        BufferedWriter bw = new BufferedWriter(writer);
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

                    //code for creating a new playlist when the list of files does not include the playlist text file
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*First try at coding this method from a stack overflow example
        PrintWriter pw = new PrintWriter(new FileOutputStream(_playlistName + ".txt"));
        if (_playlistSongs != defaultEmptyPlaylistFiles){
            for (String s : this.getPlaylistFilenamesArray())
                pw.println(s);
        }
        else{
            pw.println("empty");
            System.out.println("This is an empty playlist. No songs to save to text file.");
        }
        pw.close();*/


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
