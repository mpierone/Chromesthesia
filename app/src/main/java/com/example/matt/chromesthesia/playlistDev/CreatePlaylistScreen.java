package com.example.matt.chromesthesia.playlistDev;

/**
 * Created by Isabelle on 10/26/2016.


public class CreatePlaylistScreen extends PlayListSelectionScreen {
    View createPlaylistView;
    protected Playlist p;
    protected String playlistName;
    public CreatePlaylistScreen(){

    }
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.playlistprompt);

    }




public void saveToInternalStorage(String intMemPath, Playlist p) throws IOException {
    File intMem = new File(intMemPath);
    File playlistFile = new File (p.getNameOfTextFile());
    if (intMem.listFiles().length > 0){
        for (File file: intMem.listFiles()){
            FileOutputStream fos = openFileOutput(playlistFile.getName(), Context.MODE_PRIVATE);
            if (file.isDirectory()){
                saveToInternalStorage(file.getAbsolutePath(), p);
            }
            if (file.getAbsolutePath().toLowerCase() == playlistFile.getAbsolutePath().toLowerCase()) {
                deleteFile(file.getName());

                try {

                    if (p._playlistSongs != null) {
                        for (String s : p.getFilenamesArray()) {
                            s += "\n";
                            fos.write(s.getBytes());
                            //System.out.println("Added " + s);
                            fos.close();
                        }
                    }
                    else {
                        String empty = "empty";
                        fos.write(empty.getBytes());
                        fos.close();
                        //System.out.println("This is an empty playlist. No songs to save to text file.");
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                if (p._playlistSongs != null) {
                    for (String s : p.getFilenamesArray()) {
                        s += "\n";
                        fos.write(s.getBytes());
                        //System.out.println("Added " + s);
                        fos.close();
                    }
                }
                else {
                    String empty = "empty";
                    fos.write(empty.getBytes());
                    fos.close();
                    //System.out.println("This is an empty playlist. No songs to save to text file.");
                }
            }
            }
        }
    //System.out.println("Saved " + playlistFile + " in internal storage at " + intMemPath);
    }
}


*/