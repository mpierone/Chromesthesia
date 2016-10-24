
package com.example.matt.chromesthesia;
import com.example.matt.chromesthesia.MPC.binder_music;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;
import com.example.matt.chromesthesia.playlistDev.*;
import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.View;
import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Chromesthesia extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    protected ArrayList<Song> songlist;
    protected MPC mpservice;
    private Intent player;
    private ListView songView;
    protected localMusicManager lmm;
    private boolean musicbound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        songView = (ListView)findViewById(R.id.librarylist);
        setContentView(R.layout.activity_chromesthesia);
        lmm = new localMusicManager();
        //System.out.println("in CHROMESTHESIA after lmm = newlmm();");
        try{
            //System.out.println("in the try block in CHROMESTHESIA");
            songlist = lmm.makeSongsList();
            //System.out.println((songlist.size()));
            ID3 ayy = songlist.get(0).get_id3();
            if (ayy == null) {
                //System.out.println("no id3");
            }
            else{
                //System.out.println(ayy.getTitle());
            }

        }
        catch(Exception e){
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

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        setContentView(R.layout.homescreen);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        //mViewPager = (ViewPager) findViewById(RelativeLayout.generateViewId());
        //mViewPager.setAdapter(mSectionsPagerAdapter);
        Button libraryButton = (Button) findViewById(R.id.libraryButton);
        libraryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent libraryIntent = new Intent(view.getContext(), Library.class);
                startActivityForResult(libraryIntent, 0);
            }
        });
        Button playlistButton = (Button) findViewById(R.id.playlistButton);
        playlistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent playlistIntent = new Intent(view.getContext(), PlayList.class);
                startActivityForResult(playlistIntent, 0);
            }
        });
        Button playScreenButton = (Button) findViewById(R.id.playscreenTestButton);
        playScreenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent playScreenIntent = new Intent(view.getContext(), NowPlayingScreen.class);
                startActivityForResult(playScreenIntent, 0);
            }
        });}
    public void playSongPrint(View view) {
        System.out.println("WE CLICKED!");
    }
    public void playSong(View view) throws NullPointerException{
        try {
            System.out.println("Hey we're trying to play songs now!");
            System.out.println(Integer.parseInt(view.getTag().toString()));
            mpservice.setPlaying(Integer.parseInt(view.getTag().toString()));
            mpservice.playsong();
        }
        catch (NullPointerException n){
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
            mpservice.setSngs(songlist);
            musicbound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicbound = false;
        }
    };
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chromesthesia, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
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
    protected void onStart (){
        super.onStart();
        if(player==null){
            player = new Intent(this,MPC.class);
            bindService(player, musicconnect, Context.BIND_AUTO_CREATE);
            startService(player);
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        public PlaceholderFragment() {
        }
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_chromesthesia, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}