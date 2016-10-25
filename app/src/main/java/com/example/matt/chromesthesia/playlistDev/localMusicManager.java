package com.example.matt.chromesthesia.playlistDev;

import android.os.Environment;
import android.util.Log;

import com.example.matt.chromesthesia.Song;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by Isabelle on 10/4/2016.
 *
 * This class reads the files from the SD card.
 * Detects any audio files
 */




public class localMusicManager {


    final String SD_LOCATION = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
    File sdlocation = new File(SD_LOCATION + "/Download/");

    private ArrayList<Song> _songsList;
    private ArrayList<Song> _currentPlaylist;

    public localMusicManager() {
        //constructor
        _songsList = new ArrayList<>();
    }

    /*
    Detects audio files with extensions: .mp3, .mp4a, and .wav
     */
    class musicFinder implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return (name.toLowerCase().endsWith(".mp3") || dir.isDirectory());
        }
    }


    /*getter for SD_LOCATION*/
    public String getSD_LOCATION() {
        return SD_LOCATION;
    }

    //Method for checking if songList already has songID stored in it.
    public boolean trackInSongList(ArrayList songsList, int songID) {
        return songsList.contains(songID);
    }

    /*getter for the path of the audio file on the SD Card*/
    public String getPath(File track) {
        return track.getAbsolutePath();
    }

    public ArrayList<Song> makeSongsList() throws Exception {
        try {
            File sdCard = new File(SD_LOCATION);
            if (sdCard.listFiles().length > 0) {
                for (File file : sdCard.listFiles(new musicFinder())) {
                    //sets up String[] of track info
                    //System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        System.out.println("AYYYYYY\n\n\n\n");
                        System.out.println(file.getAbsoluteFile());
                        makeSongsList(file.getAbsolutePath());
                    } else if (file.getAbsolutePath().toLowerCase().endsWith(".mp3")) {
                        Song so = new Song(file.getAbsolutePath());
                        if (so == null) {
                            System.out.println("so == null!\n");
                        }
                        //String thetitle = so.get_id3().getTitle();
                        //System.out.println(thetitle);
                        //System.out.println(so.get_id3().getArtist());
                        //System.out.println(so.get_id3().getAlbum());
                        _songsList.add(so);
                    }
                }
            }
            //System.out.println(_songsList.size());
        } catch (Exception e) {
            Log.e("lmm", "err setting datasource", e);
        }
        return _songsList;
    }

    private ArrayList<Song> makeSongsList(String folder) throws Exception {
        try {
            File sdCard = new File(folder);
            if (sdCard.listFiles().length > 0) {
                for (File file : sdCard.listFiles(new musicFinder())) {
                    /*Recursively call to find subfolders*/
                    if (file.isDirectory()) {
                        makeSongsList(file.getAbsolutePath());
                    }
                    /*find mp3 files*/
                    else if (file.getAbsolutePath().toLowerCase().endsWith(".mp3")) {
                        Song so = new Song(file.getAbsolutePath());
                        if (so == null) {
                            System.out.println("so == null!\n");
                        }
                        //String thetitle = so.get_id3().getTitle();
                        //System.out.println(thetitle);
                        //System.out.println(so.get_id3().getArtist());
                        //System.out.println(so.get_id3().getAlbum());
                        _songsList.add(so);
                    }
                }
            }
            //System.out.println(_songsList.size());
        } catch (Exception e) {
            Log.e("lmm", "err setting datasource", e);
        }
        return _songsList;
    }


}