package com.example.matt.chromesthesia.playlistDev;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Dave on 10/22/2016.
 */

public class SongAdapter extends ArrayAdapter {

    public SongAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
