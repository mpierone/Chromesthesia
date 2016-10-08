package com.example.matt.chromesthesia;

import com.example.matt.chromesthesia.playlistDev.mp3Parser;

import org.junit.Test;

public class tagParserTests {

    /* The ID3 class will replace the String[] in the hashmap of the Song class.
        This avoids redundancy of getting id3 tag info just to input it into the Song's String[];
        **Without the String[] we won't have the "append tags to the end of it" functionality I originally imagined.
        * Instead, we can use the comments section to store tag information, and add methods and a view
        * for users and developers to add their own tags to a song's comment section.
        * We should eventually be able to parse through the comments and be able to sort songs by these tags.

        For the sake of testing we might make the filepath for the localMusicManager to rummage through
        the raw directory in res.

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

*/
    @Test
    public void getTagInfoMP3() throws Exception {
        mp3Parser example = new mp3Parser();
        example.printID3("C:\\Users\\Isabelle\\Chromesthesia\\app\\src\\main\\res\\raw\\rumine.mp3");
    }
/* For some reason I keep getting this error:
java.io.IOException: Could not delete temporary file C:\Users\Isabelle\AppData\Local\Temp\apache-tika-3078004099057068045.tmp

For Sprint 1 I'll keep it narrowed down to .mp3 files. Will add other formats for Sprint 2.

    @Test
    public void getTagInfoMP4() throws Exception{
        mp3Parser example = new mp3Parser();
        example.parseMP4("C:\\Users\\Isabelle\\Chromesthesia\\app\\src\\main\\res\\raw\\les.m4a");
    }
*/


}
