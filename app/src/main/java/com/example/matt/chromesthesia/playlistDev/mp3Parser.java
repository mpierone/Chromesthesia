package com.example.matt.chromesthesia.playlistDev;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Isabelle on 10/6/2016.
 */

public class mp3Parser {
    public void parseIt() throws Exception, IOException, SAXException, TikaException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Isabelle\\Chromesthesia\\app\\src\\main\\res\\raw\\rumine.mp3"));
        ParseContext pcontext = new ParseContext();

        Mp3Parser mParse = new Mp3Parser();
        mParse.parse(inputStream, handler, metadata, pcontext);
        LyricsHandler lyrics = new LyricsHandler(inputStream,handler);

        //while(lyrics.hasLyrics()){} can be used to parse lyrics from it but we don't need it for this project

        String[] tagNames = metadata.names();

        for (String tag : tagNames){
            System.out.println(tag + ": " + metadata.get(tag));
        }

    }
}
