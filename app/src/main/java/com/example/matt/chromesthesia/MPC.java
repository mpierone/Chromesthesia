package com.example.matt.chromesthesia;

// MPC = MEDIA PLAYER CLASS

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.matt.chromesthesia.enums.Repeat;

import java.util.ArrayList;

/**
 * Created by Will Stewart on 9/27/2016. yay
 */
public class MPC extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{
    private MediaPlayer mediaPlayer;
    private ArrayList<Song> songs;
    private Song playing;
    private int songposition;
    private Repeat Loop;
    private final IBinder bindme = new binder_music();

    public void onCreate(){
        super.onCreate();
        Loop = Repeat.ALL;
        songposition = 0;
        mediaPlayer = new MediaPlayer();
        mp_init();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return bindme;
    }
    @Override
    public boolean onUnbind (Intent intent) {
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }
    public void mp_init(){
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    public void startplay (){
        mediaPlayer.reset();
        Song playme = songs.get(songposition);
        String nowplaying = playme.get_identification();
        //Uri trackid = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, nowplaying.longValue());
        try{
            mediaPlayer.setDataSource(getApplicationContext(),Uri.parse(nowplaying));
        }
        catch(Exception e) {
            Log.e("MPC","data source error",e);
            }
        mediaPlayer.prepareAsync();
    }
   // }
    public void setSngs(ArrayList<Song> Sngs) {
        songs = Sngs;
    }
    public ArrayList<Song> getSongs () { return songs;}
    public void stop_pb(){
        mediaPlayer.pause();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.reset();
        System.out.println("we're in oncompletion and songposition is:  " + songposition);
        if (Loop == null) {
            System.out.println("WHY IS LOOP NULL?");
        }
        System.out.println("onCompletion listener and loop is:  " + Loop);
        switch(Loop) {
            case ALL:
                songposition = (songposition+1) % songs.size();
                break;
            case ONE:
                System.out.println("repeat is set to one");
                break;
            case NONE:
                System.out.println(songposition);
                songposition = songposition + 1;
                System.out.println("repeat is set to NONE:  songpos = "+ songposition + " and songsizse = " + songs.size());
                if (songposition == songs.size()) {
                    songposition = 0;
                    mp.reset();
                }
                break;
        }
        if (songposition > -1) {
            Song nextsong = songs.get(songposition);
            String currentsong = nextsong.get_identification();
            try {
                mp.setDataSource(currentsong);
            }
            catch (Exception e){
                Log.e("mpc","err setting datasource on continuous playback", e);
            }
            mp.prepareAsync();
        }
        else {
           // mp.reset();
        }
    }

    public void pauseSong() {
        mediaPlayer.pause();
    }

    public void resumePlay() {
        mediaPlayer.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
    public void setPlaying(int index) {
        songposition = index;
    }

    //grabs previous song in playlist. If out of bounds start at last index
    public void playPrevious() {
        if (songposition - 1 < 0) {
            songposition = songs.size() - 1;
            playsong();
        } else {
            //System.out.println(songposition);
            songposition -= 1;
            playing = songs.get(songposition);
            //System.out.println(songposition);
            playsong();
        }
    }

    //plays next song. If next would go out of bounds then go to index 0
    public void playNext() {
        if (songposition + 1 > songs.size() - 1) {
            songposition = 0;
            playsong();
        } else {
            songposition += 1;
            playing = songs.get(songposition);
            playsong();
        }
    }

    public void playsong(){
        mediaPlayer.reset();

        playing = songs.get(songposition);

        String currentsong = playing.get_identification();
        //Uri songuri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,playing);
        try{
            mediaPlayer.setDataSource(currentsong);
        }
        catch(Exception e){
            Log.e("mpc","err setting datasource", e);
        }
        mediaPlayer.prepareAsync();
    }
    public void setLoop(String setting) {
        System.out.println("we've called setLoop! and setting is:  "+setting);
        switch (setting) {
            case "ALL":
                Loop = Repeat.ALL;
                break;
            case "ONE":
                Loop = Repeat.ONE;
                break;
            case "NONE":
                Loop = Repeat.NONE;
        }
    }
    public class binder_music extends Binder {
        MPC getservice () {
            return MPC.this;
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void continueSong() {
        Song playing = songs.get(songposition);
        String currentsong = playing.get_identification();
        //Uri songuri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,playing);
        try {
            mediaPlayer.setDataSource(currentsong);
        } catch (Exception e) {
            Log.e("mpc", "err setting datasource", e);
        }
        mediaPlayer.prepareAsync();
    }

    //seek bar methods
    int position; //time position of song


    public int getPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public void seek(int position) {
        mediaPlayer.seekTo(position);
    }

    public String getName() {
        return songs.get(songposition).get_id3().getTitle();
    }

    public String getArtist() {
        return songs.get(songposition).get_id3().getArtist();
    }
}

