
package com.example.matt.chromesthesia;
import com.example.matt.chromesthesia.MPC.binder_music;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;
import com.example.matt.chromesthesia.playlistDev.*;
import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.widget.Button;
import android.widget.Toast;

import static android.support.v7.appcompat.R.styleable.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matt.chromesthesia.MPC.binder_music;
import com.example.matt.chromesthesia.playlistDev.ID3;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class Chromesthesia extends AppCompatActivity{
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private FragmentPagerStateAdapter fragmentPagerStateAdapter;
    protected ArrayList<Song> songlist;
    protected ArrayList<Song> songlist_artist;
    protected ArrayList<Song> songlist_album;
    protected ArrayList<Song> songlist_title;
    protected ArrayList<Song> _OGsongs;
    public ArrayList<Song> nowPlaying;
    public ArrayList<String> playQueueNames = new ArrayList<>();
    public ArrayList<String> _OGNames = new ArrayList<>();
    public MPC mpservice;
    private Intent player;
    private ListView songView;
    protected localMusicManager lmm;
    private boolean musicbound = false;
    protected MPC media = new MPC();
    public int positionVar;//testing this
    public int myVersion = Build.VERSION.SDK_INT;
    public int myLollipop = Build.VERSION_CODES.LOLLIPOP_MR1;
    private Fragment libraryFragment;
    private Fragment nowPlayingFragment;
    private Fragment playlistFragment;
    public Fragment spotifyFragment;





    private Fragment libraryArtistFragment;
    private Fragment libraryAlbumFragment;
    private Fragment libraryTitleFragment;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spotifyIntent = new Intent(this,SpotiPlayer.class);

        if (myVersion > myLollipop) {
            if (!checkIfAlreadyHavePermissions()) {
                requestAllPermissions();
            }
        }
        songView = (ListView) findViewById(R.id.librarylist);
        lmm = new localMusicManager();
        try {
            songlist = lmm.makeSongsList();
            songlist_album = new ArrayList<>(songlist);
            songlist_artist = new ArrayList<>(songlist);
            songlist_title = new ArrayList<>(songlist);
            _OGsongs = new ArrayList<>(songlist);
            nowPlaying = new ArrayList<>();
            Collections.sort(songlist_album, new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    String one = o1.get_id3().getAlbum();
                    String two = o2.get_id3().getAlbum();
                    if (one == null) {
                        return 1;
                    }
                    else if (two == null) {
                        return -1;
                    }
                    else if (one == null && two == null) {
                        return 0;
                    }
                    else {
                        return one.compareTo(two);
                    }
                }
            });
            Collections.sort(songlist_artist, new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    String one = o1.get_id3().getArtist();
                    String two = o2.get_id3().getArtist();
                    if (one == null) {
                        return 1;
                    }
                    else if (two == null) {
                        return -1;
                    }
                    else if (one == null && two == null) {
                        return 0;
                    }
                    else {
                        return one.compareTo(two);
                    }
                }
            });
            Collections.sort(songlist_title, new Comparator<Song>() {
                @Override
                public int compare(Song o1, Song o2) {
                    String one = o1.get_id3().getTitle();
                    String two = o2.get_id3().getTitle();
                    if (one == null) {
                        return 1;
                    }
                    else if (two == null) {
                        return -1;
                    }
                    else if (one == null && two == null) {
                        return 0;
                    }
                    else {
                        return one.compareTo(two);
                    }
                }
            });

        } catch (Exception e) {
            Log.e("chromesthesia", "err setting datasource", e);
        }
        //ActivityCompat.requestPermissions(, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
        if (player == null) {
            System.out.println("PLAYER IS NULL!");
            Toast.makeText(Chromesthesia.this, "PLAYER IS NULL!", Toast.LENGTH_LONG).show();
            System.out.println("songlist is:  " + songlist.size());
            player = new Intent(this, MPC.class);
            bindService(player, musicconnect, Context.BIND_AUTO_CREATE);
            startService(player);
            if (mpservice == null) {
                System.out.println("why am I Null?!?!");
            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragmentPagerStateAdapter = new FragmentPagerStateAdapter(getSupportFragmentManager());
        libraryFragment = new Library();
        libraryAlbumFragment = new Library_album();
        libraryArtistFragment = new Library_artist();
        libraryTitleFragment = new Library_title();
        fragmentPagerStateAdapter.addFragments(libraryFragment, "Library");
        fragmentPagerStateAdapter.addFragments(libraryAlbumFragment, "Album Sort");
        fragmentPagerStateAdapter.addFragments(libraryArtistFragment, "Artist Sort");
        fragmentPagerStateAdapter.addFragments(libraryTitleFragment, "Title Sort");
        nowPlayingFragment = new NowPlayingScreen();
        fragmentPagerStateAdapter.addFragments(nowPlayingFragment, "Now Playing");
        playlistFragment = new PlayList();
        fragmentPagerStateAdapter.addFragments(playlistFragment, "Playlist");
        //spotifyFragment = new SpotiPlayer();
        //fragmentPagerStateAdapter.addFragments(spotifyFragment, "Spotify Player");
        //fragmentPagerStateAdapter.addFragments(playlistFragment, "Playlist");
        viewPager.setAdapter(fragmentPagerStateAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setEnabled(true);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        /***Spotify***/
        // Request code that will be used to verify if the result comes from correct activity
        // Can be any integer

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    public void playSongPrint(View view) {
        System.out.println("WE CLICKED!");
    }

    public void playSong(View view, int id) throws NullPointerException {
        try {
            System.out.println("Hey we're trying to play songs now!");
            //System.out.println(Integer.parseInt(view.getTag().toString()));
            System.out.println("our position we're trying to play is:  " + id);
            mpservice.setPlaying(id);
            mpservice.playsong();
        } catch (NullPointerException n) {
            Log.e("Error: ", "No song to play", n);
        }
    }
    private ServiceConnection musicconnect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder_music binder = (binder_music) service;
            Toast.makeText(Chromesthesia.this, "We're in onServiceConnected!", Toast.LENGTH_LONG).show();
            System.out.println("we're in onServiceConnected!");
            mpservice = binder.getservice();
            mpservice.setSngs(nowPlaying);
            musicbound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicbound = false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chromesthesia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        if (player == null) {
            player = new Intent(this, MPC.class);
            bindService(player, musicconnect, Context.BIND_AUTO_CREATE);
            startService(player);
        }
    }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mpservice.isPlaying()) {
            mpservice.stop_pb();

        }
        if (musicbound) {
            unbindService(musicconnect);
            musicconnect = null;
        }

    }

    /*
    * Here we check if we already have the permissions that Chromesthesia uses.
    * The names are a bit cryptic:
    *
    * sdR = sd card 'Read' external storage
    * sdW = sd card 'Write' external storage
    * internet = permission for internet
    * bt = regular bluetooth
    * btA = bluetooth admin
    * btP = bluetooth privileged
    * wl = Wake Lock permission
    * mcc = Media Content Control permission
    * mas = Modify Audio Settings permission
    *
    * That's all the permissions right now!
    * */
    private boolean checkIfAlreadyHavePermissions() {
        int sdR = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int sdW = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int bt = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);
        int btA = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN);
        int btP = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_PRIVILEGED);
        int wl = ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK);
        int mcc = ContextCompat.checkSelfPermission(this, Manifest.permission.MEDIA_CONTENT_CONTROL);
        int mas = ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS);

        if ((sdR != 1) && (sdW != 1) && (internet != 1) && (bt != 1) && (btA != 1) && (btP != 1) && (wl != 1) && (mcc != 1) && (mas != 1)) {
            return false;
        } else {
            return true;
        }
    }

    private void requestAllPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_PRIVILEGED,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.MEDIA_CONTENT_CONTROL,
                Manifest.permission.MODIFY_AUDIO_SETTINGS}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public ArrayList<String> getPlayQueueNames() {
        return lmm.makeSongNames(nowPlaying);
    }
}
    @Override
    public void onStop() {
        super.onStop();
        client.disconnect();
    }



}