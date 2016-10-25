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


    /**using this in saving the playlist txt files to the sd card*/
    public File getPlaylistStorageDirectory(){
        //directory creation
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), ".txt");
        if (!file.mkdirs()){
            System.out.println("Playlist Directory not created");
        }
        return file;
    }

    /*Saving a playlist into a string array into a text file
    * MUST TEST FOR: At creation of a playlist savePlaylist() txt doc is...
    * _playlistName.txt:
    *   empty
    *
    * If I use setPlaylistFiles and save again, I need to make sure it clears the contents of _playlistname.txt
    * and replaces it with the new arraylist of audio files to find
    * */
    public void savePlaylist() throws FileNotFoundException {

        File file = new File(_playlistName + ".txt");
        try {
            if(!file.exists())
                file.createNewFile();
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            if (_playlistSongs != defaultEmptyPlaylistFiles){
                for (String s : this.getPlaylistFilenamesArray())
                    bw.append(s);
                    bw.append("\n");
            }else{
                bw.append("empty");
                System.out.println("This is an empty playlist. No songs to save to text file.");
            }
            bw.close();
            writer.close();
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
    private ArrayList<String> loadPlaylist(File txtfilename) throws FileNotFoundException {
        ArrayList<String> storedPlaylist = new ArrayList<>();
        Scanner scan = new Scanner(txtfilename);
        while (scan.hasNext()) {
            storedPlaylist.add(scan.next());
        }
        return storedPlaylist;
    }

    /*Taking the loaded playlist and checking if a file found on the SDCard is in the playlist
    * false = not in playlist, so don't include in the Playlist object's songs
    * true = in playlist, add the song to the Playlist object's songs*/
    private boolean boolAddToPlaylist(File txtFilename,String sdFilename) throws FileNotFoundException {
        return loadPlaylist(txtFilename).contains(sdFilename);
    }


    /*Add songs to playlist if they are supposed to be there*/

    public ArrayList<Song> populatePlaylist(String folder, File txtSavedPlaylist) throws Exception {
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
                            && boolAddToPlaylist(txtSavedPlaylist, file.getName()) == true
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
