package com.example.matt.chromesthesia.playlistDev;

import android.util.Log;

import com.example.matt.chromesthesia.Song;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Isabelle on 10/25/2016.
 *
 * Playlist object
 */

public class Playlist {
    String _playlistName;
    ArrayList<Song> _playlistSongs;
    ArrayList<Song> defaultEmptyPlaylistFiles;
    File _playlistFile;
    public Playlist(String name, File playlistTxtDoc){
        //constructor
        _playlistName = name;
        _playlistFile = playlistTxtDoc;
        _playlistSongs = defaultEmptyPlaylistFiles;

    }

    public void setPlaylistName(String playlistName){
        _playlistName = playlistName;
    }

    public File get_playlistTxtFile(){
        return _playlistFile;
    }

    public String getPlaylistName(){
        return _playlistName;
    }

    public void setPlaylistFiles(ArrayList<Song> playlistSongs){
        _playlistSongs = playlistSongs;
    }

    public ArrayList<Song> getPlaylistAudioFiles(){
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


    /*Taking the loaded playlist and checking if a file found on the SDCard is in the playlist
    * false = not in playlist, so don't include in the Playlist object's songs
    * true = in playlist, add the song to the Playlist object's songs*/
    private boolean boolAddToPlaylist(File txtFilename,String sdFilename) throws FileNotFoundException {
        PlaylistManager pm = new PlaylistManager();

        return pm.loadPlaylist(txtFilename).contains(sdFilename);
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
