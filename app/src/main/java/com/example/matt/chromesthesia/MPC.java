package com.example.matt.chromesthesia;

// MPC = MEDIA PLAYER CLASS

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.example.matt.chromesthesia.Chromesthesia;
import java.math.BigInteger;
import java.util.ArrayList;
import android.net.Uri;
import android.os.Binder;
import android.os.PowerManager;
import android.util.Log;
import com.example.matt.chromesthesia.enums.*;

import java.io.FileNotFoundException;

/**
 * Created by Will Stewart on 9/27/2016. yay
 */
public class MPC extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{
    private MediaPlayer mediaPlayer;
    private ArrayList<Song> songs;
    private int songposition;
    private Repeat Loop;
    private final IBinder bindme = new binder_music();

    public void onCreate(){
        super.onCreate();
        Repeat Loop = Repeat.ALL;
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
        // make it stop MAKE IT STOP
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.reset();

        switch(Loop) {
            case ALL:
                songposition = (songposition+1) % songs.size();
                break;
            case ONE:
                System.out.println("repeat is set to one");
                break;
            case NONE:
                System.out.println("repeat is set to NONE");
                songposition++;
                if (songposition > songs.size()-1) {
                    songposition = -1;
                }
                break;
        }
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
    public void playsong(){
        mediaPlayer.reset();
        Song playing = songs.get(songposition);
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
    public class binder_music extends Binder {
        MPC getservice () {
            return MPC.this;
        }
    }
}

