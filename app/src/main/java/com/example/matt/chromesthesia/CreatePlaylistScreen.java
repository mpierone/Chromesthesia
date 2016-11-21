package com.example.matt.chromesthesia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.matt.chromesthesia.playlistDev.Playlist;
import com.example.matt.chromesthesia.playlistDev.PlaylistManager;
import com.example.matt.chromesthesia.playlistDev.localMusicManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Isabelle on 10/26/2016.
 *
 */
 public class CreatePlaylistScreen extends Fragment {
 public View createPlaylistView;
 protected Playlist p;
 protected String playlistName;
    private View rootView;
 private Button saveButton;
 private EditText userInput;
 private ListView playlistLibraryView;
 private ArrayList<Song> songs;
 private ArrayList<String> songArray;
 private FileOutputStream fos;
 private File playlistFile;
 private PlaylistManager plmanager;
 private ArrayList<String> toInsertIntofos = new ArrayList<>();

 Chromesthesia chromesthesia;
 String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();

 public CreatePlaylistScreen(){
 }
 @Override
 public void onAttach(Activity activity) {
  super.onAttach(activity);
  chromesthesia = (Chromesthesia) getActivity();
  songs = new ArrayList(chromesthesia.songlist);
  localMusicManager lmm = new localMusicManager();
  songArray =  lmm.makeSongNames(chromesthesia.songlist);
  Collections.unmodifiableList(songArray);
  Collections.unmodifiableList(songs);
 }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.playlistprompt, container, false);
     plmanager = new PlaylistManager();
     userInput = (EditText) rootView.findViewById(R.id.inputPlaylistName);
        saveButton = (Button) rootView.findViewById(R.id.addSongsSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
          BufferedWriter writer = null;
          try {
           String name = userInput.getText().toString();
           System.out.println(userInput.getText().toString());
           plmanager.savePlaylist(p, userInput.getText().toString(), fos);
           writer = new BufferedWriter(new FileWriter(name));
           for (int i = 0; i < toInsertIntofos.size(); i++) {
            writer.write(toInsertIntofos.get(i));
            writer.newLine();
           }
           saveToInternalStorage(p);
          } catch (IOException e) {
           e.printStackTrace();
          }
          finally {
           if (writer!=null) try {
            writer.close();
           } catch (IOException e) {
            e.printStackTrace();
           }
          }
         }
        });
        return rootView;

    }

 @Override
 public void onActivityCreated(Bundle savedInstanceState) {
  super.onActivityCreated(savedInstanceState);
  final ArrayList<String> songarr = new ArrayList(songArray);
  playlistLibraryView = (ListView) rootView.findViewById(R.id.list);
  playlistLibraryView.setAdapter(new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, songarr));
  playlistLibraryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
     p = new Playlist(userInput.getTransitionName());
     playlistFile = new File (p.getNameOfTextFile());
     try {
      fos = getContext().openFileOutput(playlistFile.getName(), getContext().MODE_PRIVATE);
     } catch (FileNotFoundException e) {
      e.printStackTrace();
     }
     System.out.println(chromesthesia.storeSong(view, position));
     toInsertIntofos.add(chromesthesia.storeSong(view, position));

    if (chromesthesia.mpservice.isPlaying()) {
     TextView songTitle = (TextView) rootView.findViewById(R.id.songTitleText);
     songTitle.setText(chromesthesia.mpservice.getName());
     songTitle.setTextColor(Color.BLUE);
     TextView artistName = (TextView) rootView.findViewById(R.id.artistText);
     artistName.setText(chromesthesia.mpservice.getArtist());
     artistName.setTextColor(Color.BLUE);
    }
   }
  });
 }
 public void saveToInternalStorage(Playlist p) throws IOException {
  String intMemPath = baseDir;
  File intMem = new File(intMemPath);
 if (intMem.listFiles().length > 0){
 for (File file: intMem.listFiles()){
 if (file.getAbsolutePath().toLowerCase() == playlistFile.getAbsolutePath().toLowerCase()) {
 getContext().deleteFile(file.getName());
 try {
 if (p._playlistSongs != null) {
 for (String s : p.getFilenamesArray()) {
 s += "\n";
 fos.write(s.getBytes());
 System.out.println("Added " + s);
 fos.close();
 }
 }
 else {
 String empty = "empty";
 fos.write(empty.getBytes());
 fos.close();
 System.out.println("This is an empty playlist. No songs to save to text file.");
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
 System.out.println("Added " + s);
 fos.close();
 }
 }
 else {
 String empty = "empty";
 fos.write(empty.getBytes());
 fos.close();
 System.out.println("This is an empty playlist. No songs to save to text file.");
  System.out.println("Testing");
 }
 }
 }
 }
 System.out.println("Saved " + playlistFile.getName() + " in internal storage at " + intMemPath);
 }
 }