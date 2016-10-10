package com.example.matt.chromesthesia.playlistDev;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.matt.chromesthesia.R;
import com.example.matt.chromesthesia.Song;
/**
 * Created by Mikeys_Mac on 10/10/16.
 */
public class SongAdapter extends BaseAdapter {
    private ArrayList<Song> songList;
    private ListView songView;
    private LayoutInflater layoutInf;
    private ArrayList<Song> songs;
    public SongAdapter(Context c, ArrayList<Song> theSongs){
        songs=theSongs;
        layoutInf=LayoutInflater.from(c);
    }
    @Override
    public int getCount() {
        return 0;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //map to song layout
        LinearLayout songLay = (LinearLayout)layoutInf.inflate
                (R.layout.libraryscreen, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.songtitle);
        TextView artistView = (TextView)songLay.findViewById(R.id.artistname);
        //get song using position
        Song currSong = songs.get(position);
        //get title and artist strings
        songView.setText(currSong.get_id3().getTitle());
        artistView.setText(currSong.get_id3().getArtist());
        System.out.println(currSong.get_id3().getTitle());
        System.out.println(currSong.get_id3().getArtist());
        //set position as tag
        songLay.setTag(position);
        return songLay;
    }
}