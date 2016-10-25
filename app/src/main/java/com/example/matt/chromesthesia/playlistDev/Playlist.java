package com.example.matt.chromesthesia.playlistDev;

import android.util.Log;

import com.example.matt.chromesthesia.Song;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Isabelle on 10/25/2016.
 *
 * Playlist object
 */

public class Playlist {
    String _playlistName;
    ArrayList<Song> _playlistSongs;
    ArrayList<Song> defaultEmptyPlaylistFiles;
    public Playlist(String name){
        //constructor
        _playlistName = name;
        _playlistSongs = defaultEmptyPlaylistFiles;
    }

    public void setPlaylistName(String playlistName){
        _playlistName = playlistName;
    }

    public String getPlaylistName(){
        return _playlistName;
    }

    public void setPlaylistFiles(ArrayList<Song> playlistSongs){
        _playlistSongs = playlistSongs;
    }

    public ArrayList<Song> getPlaylistFiles(){
        return _playlistSongs;
    }

    public ArrayList<String> getPlaylistFilenamesArray(){
        ArrayList<String> filenames = new ArrayList<>();
        for (Song s : _playlistSongs){
            filenames.add(s.get_audioFilePath());
        }
        return filenames;
    }


    /*For use in playlist creation*/

    /*Saving a playlist into a string array into a text file*/
    public void savePlaylist(String fileName, ArrayList<Song> playlist) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
        for (Song s : playlist)
            pw.println(s.get_audioFilePath());
        pw.close();
    }

    public boolean boolAddToPlaylist(String sdFilename, File txtSavedPlaylist) throws FileNotFoundException {
        ArrayList<String> storedPlaylist = new ArrayList<>();
        Scanner scan = new Scanner(txtSavedPlaylist);
        while (scan.hasNext()) {
            storedPlaylist.add(scan.next());
        }

        return storedPlaylist.contains(sdFilename);
    }

    private ArrayList<Song> populatePlaylist(String folder, File txtSavedPlaylist) throws Exception {
        localMusicManager lmm = new localMusicManager();
        try {
            File sdCard = new File(folder);
            if (sdCard.listFiles().length > 0) {
                for (File file : sdCard.listFiles()){
                    /*Recursively call to find subfolders*/
                    if (file.isDirectory()) {
                        populatePlaylist(file.getAbsolutePath(), txtSavedPlaylist);
                    }
                    /*here is where we'll find our songs stored in a text file
                    * if the Arraylist<> read from the txt file contains the filename found on the SD Card, add it to the songsList
                    * If not, then skip it
                    * */
                    else if (file.getAbsolutePath().toLowerCase().endsWith(".mp3")
                            && boolAddToPlaylist(file.getName(),txtSavedPlaylist) == true
                            ) {
                        Song so = new Song(file.getAbsolutePath());
                        if (so == null) {
                            System.out.println("so == null!\n");
                        }
                        //String thetitle = so.get_id3().getTitle();
                        //System.out.println(thetitle);
                        //System.out.println(so.get_id3().getArtist());
                        //System.out.println(so.get_id3().getAlbum());
                        _playlistSongs.add(so);
                    }
                }
            }
            //System.out.println(_songsList.size());
        } catch (Exception e) {
            Log.e("lmm", "err setting datasource", e);
        }
        return _playlistSongs;
    }

}
