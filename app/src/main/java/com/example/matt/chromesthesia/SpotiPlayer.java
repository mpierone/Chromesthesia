package com.example.matt.chromesthesia;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * New implementation of Spotify resources after running into issues with the Spotify SDK
 */
public class SpotiPlayer extends AppCompatActivity {


    SpotifyApi api;
    TextView songInfo;

    public static Intent createIntent(Context context) {
        return new Intent(context, SpotiPlayer.class);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        songInfo = (TextView) findViewById(R.id.spotify_song_info);


        findViewById(R.id.spotify_scroll).canScrollVertically(1);
        api = new SpotifyApi();

        SpotifyService spotify = api.getService();


        spotify.getTrack("4jzRvmHo5owQBLa7tNH5gL", new Callback<Track>() {
            @Override
            public void success(Track track, Response response) {
                String _track = track.name;

                songInfo.setText(songInfo.getText() + "\"" + _track + "\"" + "\n");
                Log.d("Track success", track.name);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Track Failure", error.toString());
            }
        });

        spotify.getAlbum("5kfswv2yaEfIluk5qRaaYO", new Callback<Album>() {
            @Override
            public void success(Album album, Response response) {
                String _album = album.name;
                songInfo.setText(songInfo.getText() + "Off Album: " + _album + "\n");
                Log.d("Album success", album.name);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Album failure", error.toString());
            }
        });

        spotify.getArtist("1qxSVLNqRcdhXPkE6suUlN", new Callback<Artist>() {
            @Override
            public void success(Artist artist, Response response) {
                String _artist = artist.name;
                songInfo.setText(songInfo.getText() + "By: " + _artist);
                Log.d("Artist success", artist.name);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Artist failure", error.toString());
            }
        });

        ImageButton play = (ImageButton) findViewById(R.id.play_Spotify_Sample);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView web = (WebView) findViewById(R.id.spotify_web_view);
                web.setClickable(true);
                web.setWebChromeClient(new WebChromeClient());
                web.setSoundEffectsEnabled(true);
                web.canGoBackOrForward(5);
                web.getSettings().setJavaScriptEnabled(true);
                web.requestFocus(View.FOCUS_DOWN);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    web.setContextClickable(true);
                }
                web.bringToFront();

                web.loadUrl("https://open.spotify.com/track/4jzRvmHo5owQBLa7tNH5gL");
            }
        });



        Button goBack = (Button) findViewById(R.id.back_to_chromesthesia);
        goBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SpotiPlayer.this,Chromesthesia.class);
                startActivity(in);
            }
        });


    }




}