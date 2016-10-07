package com.example.matt.chromesthesia;

import com.example.matt.chromesthesia.playlistDev.Tag;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class tagParserTests {

    /* The Tag class will replace the String[] in the hashmap of the Song class.
        This avoids redundancy of getting id3 tag info just to input it into the Song's String[];
        **Without the String[] we won't have the "append tags to the end of it" functionality I originally imagined.
        * Instead, we can use the comments section to store tag information, and add methods and a view
        * for users and developers to add their own tags to a song's comment section.
        * We should eventually be able to parse through the comments and be able to sort songs by these tags.

        For the sake of testing we might make the filepath for the localMusicManager to rummage through
        the raw directory in res.
    */

    public InputStream toInputStream(File f) {
        InputStream is;
        try {
            is = new FileInputStream(f);
            is.close();
            return is;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found. Check path for accuracy.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("I/O operation interrupted or failed. Sorry!");
        }

        return null;
    }


    @Test
    public void getTagInfo() throws Exception {
        Tag exampleID3 = new Tag();
        exampleID3.readTag(new FileInputStream((new File("C:\\Users\\Isabelle\\Chromesthesia\\app\\src\\main\\res\\raw\\rumine.mp3"))) {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });
        System.out.print(
            "\n Artist: " + exampleID3.getArtist() +
                    "\n Title: " + exampleID3.getTitle() +
                    "\n Album: " + exampleID3.getAlbum() +
                    "\n Year: " + exampleID3.getYear() +
                    "\n Comments: " + exampleID3.getComment() +
                    "\n Genre: " + exampleID3.getGenre() + "\n"
        );

    }




}
