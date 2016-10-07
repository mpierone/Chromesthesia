package com.example.matt.chromesthesia.playlistDev;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.parser.mp4.MP4Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Isabelle on 10/6/2016.
 *
 * Utilized tutorial: https://www.tutorialspoint.com/tika/tika_extracting_mp3_files.htm
 *OUTPUT OF UNIT TEST:
     [0] xmpDM:genre: Alternative
     [1] creator: Arctic Monkeys
     [2] xmpDM:album: Arctic Monkeys B Sides Part II
     [3] xmpDM:trackNumber: 6/15
     [4] xmpDM:releaseDate: 2013
     [5] meta:author: Arctic Monkeys
     [6] xmpDM:artist: Arctic Monkeys
     [7] dc:creator: Arctic Monkeys
     [8] xmpDM:audioCompressor: MP3
     [9] title: R U Mine?
     [10] xmpDM:audioChannelType: Stereo
     [11] version: MPEG 3 Layer III Version 1
     [12] xmpDM:logComment: eng - iTunes_CDDB_1
     [13] D40C750F+239332+15+150+22463+38629+58510+70691+95954+110969+117042+126789+145614+166657+181327+195052+208291+227777
     [14] xmpDM:audioSampleRate: 44100
     [15] channels: 2
     [16] dc:title: R U Mine?
     [17] Author: Arctic Monkeys
     [18] xmpDM:duration: 200266.421875
     [19] Content-Type: audio/mpeg
     [20] samplerate: 44100

 So if we want:
 _title = 9
 _artist = 6
_album = 2
 _year = 4
 _genre = 0

 */

public class mp3Parser {

    public void printID3(String audioPath) throws Exception {
        ID3 test = parseMP3(audioPath);
        System.out.println(
               "Album: " + test.getAlbum() + "\n" +
                "Genre: " + test.getGenre() + "\n" +
                "Year: " + test.getYear() + "\n" +
               "Artist: " + test.getArtist() + "\n" +
                "Song Title: " + test.getTitle()
        );

    }

    public ID3 parseMP3(String audioPath) throws Exception, IOException, SAXException, TikaException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(new File(audioPath));
        ParseContext pcontext = new ParseContext();

        Mp3Parser mParse = new Mp3Parser();
        mParse.parse(inputStream, handler, metadata, pcontext);

        String[] tagNames = metadata.names();

        //parse into ID3 object

        return new ID3(metadata.get(tagNames[9]),metadata.get(tagNames[6]), metadata.get(tagNames[2]), metadata.get(tagNames[4]), metadata.get(tagNames[0]));
    }

    public void parseMP4(String audioPath)throws Exception, IOException, SAXException, TikaException{
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(new File(audioPath));
        ParseContext pcontext = new ParseContext();

        MP4Parser mParse = new MP4Parser();
        mParse.parse(inputStream, handler, metadata, pcontext);

        String[] tagNames = metadata.names();

        for (String tag : tagNames){
            System.out.println(tag + ": " + metadata.get(tag));
        }

    }
}
