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

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

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
public class SpotiPlayer extends AppCompatActivity implements Player.NotificationCallback, ConnectionStateCallback {


    SpotifyApi api;
    String TAG = "ChromesXSpot";
    private Player mPlayer;
    TextView songInfo;
    SpotifyService spotify;
    private static final String CLIENT_ID = "2de25702544a445d93cfa7d9a7c9c838";
    private static final String REDIRECT_URI = "mycallback://callback";
    private static final int REQUEST_CODE = 1337;
    public String ACCESS_TOKEN;
    public static Intent createIntent(Context context) {
        return new Intent(context, SpotiPlayer.class);
    }

    public SpotiPlayer(){
        api = new SpotifyApi();
        spotify = api.getService();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        songInfo = (TextView) findViewById(R.id.spotify_song_info);
        findViewById(R.id.spotify_scroll).canScrollVertically(1);
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
                AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                        AuthenticationResponse.Type.TOKEN,
                        REDIRECT_URI);
                builder.setScopes(new String[]{"user-read-private", "streaming"});
                AuthenticationRequest request = builder.build();
                AuthenticationClient.openLoginActivity(SpotiPlayer.this, REQUEST_CODE, request);
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

    public void printAccessToken(){
        if (ACCESS_TOKEN == null){
            Log.d(TAG, "Access token is null. Did you log in?");
        }else{
           System.out.println(ACCESS_TOKEN);
        }
    }

    /**Start of method overrides for Spotify interfaces**/
    @Override
    public void onLoggedIn() {
        Log.d(TAG, "User logged in!");
        printAccessToken();
        mPlayer.playUri(null, "spotify:track:4jzRvmHo5owQBLa7tNH5gL",0,0);

    }

    @Override
    public void onLoggedOut() {
        Log.d(TAG, "User logged out");
    }

    @Override
    public void onLoginFailed(int i) {
        Log.d(TAG,"Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d(TAG,"Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d(TAG,"Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d(TAG,"Playback event received: " + playerEvent.name());
        switch(playerEvent){
            //Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d(TAG,"Playback error received: " + error.name());
        switch(error){
            //handle error type as necessary
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == REQUEST_CODE){
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode,intent);
            if (response.getType()==AuthenticationResponse.Type.TOKEN){
                Config playerConfig = new Config(this, response.getAccessToken(),CLIENT_ID);
                mPlayer = Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver(){

                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(SpotiPlayer.this);
                        mPlayer.addNotificationCallback(SpotiPlayer.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG,"Could not initiliaze player: " +throwable.getMessage());
                    }
                });
                mPlayer.initialize(playerConfig);
                /*ACCESS_TOKEN = response.getAccessToken();
                api.setAccessToken(ACCESS_TOKEN);
                synchronized (spotify) {
                    System.out.println(spotify.getMe());
                    System.out.println(spotify.getMe().birthdate);
                    System.out.println(spotify.getMe().country);
                }*/


            }
        }
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}