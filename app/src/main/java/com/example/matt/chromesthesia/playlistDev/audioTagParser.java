package com.example.matt.chromesthesia.playlistDev;


import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;



/**
 * Created by Isabelle on 10/6/2016.
 */

public class audioTagParser extends DefaultHandler{
    private localMusicManager _lm;
    public audioTagParser(){
        //constructor
        _lm = new localMusicManager();
    }

    public String[] getTagsArray(){
        String[] trackInfo = new String[]{};

        try{
            InputStream input = new FileInputStream(new File(_lm.getSD_LOCATION()));
            DefaultHandler handler = new DefaultHandler();
            //Metadata md = new Metadata();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return trackInfo;
    }
}
