# Chromesthesia
An all new Android application for your continued music enterainment and satisfaction.  We know it's hard, trying to remember what you've listened to over the years. Chromesthesia will allow you to listen to music you enjoy from various other applications such as Spotify, SoundCloud, and Pandora through just one application! You can also locally store your music and listen to the tunes you cannot find through any of the other applications. With Chromesthesia, you will be able to keep all of the music you find from all the other applications and your local music all in one!

# To Developers Hoping to Contribute

As of 1.0.1 we're looking to debug and release playlists, check the bottom for a description of the playlist stuff we have so far as well as descriptions of the other classes that may be of use.

There are several different classes for each order for the library and they are all separate fragments using one activity, the Chromesthesia class. 

Here is a breakdown of each class:

--------Chromesthesia Class--------

This is the class where the main activity is(the class extends a AppCompatActivity). There are several methods in the class:

- On the creation of this activity, we will request permissions and set up the main view which will be an empty background but then we will also be creating each of the separate fragments we will be using for the activity. The fragments consist of the library fragment, library album fragment, library artist fragment, and library title fragment with each sorting the songs in the order of the category it contains, such as the artist. Along with the fragments, we will also need to create adapters for the fragments as well as a view pager that will allow us to swipe through the tabbed layout of the application. The fragments will be categorized by tabs to make it more simple for the users to use. The activity will also have the playback functionality in methods.

--------CreatePlayListScreen Class--------

This is the class that will be focusing on the create PlayListScreen fragment.

- This fragment's methods will rely on the functionality included in the playlistDev folder. But generally this fragment will be a screen that allows the user to create playlist by clicking on a input bar and inputing a string that will be the name of the playlist they are creating and below it will be a list view that contains the songs of the library. By clicking on the songs, the user will add the song into the playlist that they are creating. This class is still in development so it is not fully functional as of yet.

--------FragmentPagerStateAdapter--------

This is the class that allows us to manage fragments. 

- By using a FragmentPagerAdapter we will be able to manage the fragments through the various methods in this class. The methods that are in this class involve getting the number of fragments, getting the specific fragment, and getting the fragment's name from a specific position.

--------ImageAdapter--------

This class allows us to manage the images on a grid view.

- The library utilizes a gridview to create the buttons that the user will use and the grid view is managed through this image adapter.

--------Library--------

This class is a fragment that will contain the library fragment.

- The library fragment consists of the songs that the users have stored locally. The songs will be populated in the order that they are saved in the internal storage of the user's android device and it will contain a list view that will show the users their songs. The songs can be played by using that list view by clicking on the song. The library fragment also contains the several buttons that the users will be using to go to the next song, going to the previous song, pausing and playing, as well as a progress bar that shows how the song has progressed. This is all contained by a grid view that is managed by the image adapter. The fragment will rely on Chromesthesia activity which relies on the MPC class to create the mediaplayer functionality.

--------Library_album--------

This class is a fragment that will contain the library album fragment.

- The library album fragment is very similar to the library class except for the way that it is sorted. The library album fragment will sort the songs in the list view that is populated by the songs on the internal storage by the albums the songs are in. It will be sorted in the alphabetical order of the albums. The fragment will rely on Chromesthesia activity which relies on the MPC class to create the mediaplayer functionality.

--------Library_artist--------

This class is a fragment that will contain the library artist fragment.

- The library artist fragment is very similar to the library class except for the way that it is sorted. The library artist fragment will sort the songs in the list view that is populated by the songs on the internal storage by the artists who created the songs. It will be sorted in the alphabetical order of the artists. The fragment will rely on Chromesthesia activity which relies on the MPC class to create the mediaplayer functionality.

--------Library_title--------

This class is a fragment that will contain the library title fragment.

- The library title fragment is very similar to the library class except for the way that it is sorted. The library title fragment will sort the songs in the list view that is populated by the songs on the internal storage by the title in alphabetical order.The fragment will rely on Chromesthesia activity which relies on the MPC class to create the mediaplayer functionality.

--------MPC--------

This class extends the services of the mediaplayer.

- This class is essential to the application because it is the class that contains all of the media player functionality that the main activity Chromesthesia relies on. These functionalities consist of initializing the media player itself, preparing the media player, playing the songs, pausing the songs, resuming the songs, playing the previous song, playing the next song, adding songs, managing what happens after the completion of a song, and managing the identifications of the songs.

--------NowPlayingScreen--------

This class is a fragment that will contain the now playing screen fragment.

- The now playing screen will consist of the album cover centered on the screen with a seek bar that will show the progress and allow the user to scrub to certain times of the song. Below the album cover will be a previous button, a pause button, a resume/play button, and a forward button. The fragment will rely on Chromesthesia activity which relies on the MPC class to create the mediaplayer functionality.

--------PlayListSelectionScreen--------

This class is a fragment that will contain the playlistselectionscreen fragment.

- The playlist selection screen fragment will allow the user to pick which playlist they want to listen to. The playlists will be in their own list populated in a list view.

--------PlaylistContents--------

This class is a fragment that will contain the playlist contents fragment.

- The playlist contents fragment will contain the functionality of actually populating the playlist that they wish to create. 

--------SpotiPlayer--------

This class is a seperate activity from Chromesthesia that will allow the user access to the Spotify Web application.

- This activity allows for the integration of the Spotify web application into Chromesthesia to allow the user to play music from Spotify if they have premium. This class makes use of the Spotify API which allows for the playback functionality from the web application and it is linked to the Chromesthesia application. This way, Spotify can be reached from Chromesthesia.

--------Song--------

This class helps us properly label the songs.

- The song class does not provide any direct functionality to the media player but it helps place specific identifications to each audio file such as: the audio path and the identification of the audiofile. This allows us to specfically and accurately obtain the audio file that we wish to obtain. We have labeled this audio file as a song.

--------playQueueAdapter--------

This class is an ExpandableListAdapter that will help us manage the queue of the now playing list.

- In this application, the user will be able to expand a list from the now playing screen that will allow the user to add songs to be played next or at the end of the queue. That functionality relies on this adapter. The adapter specifically uses only one list for the view.

********playlistDev********

This folder contains several classes that are involved with allowing the user to create custom playlists. This is still in development because we are still working on the playlists.

--------ID3---------

- This class helps create the identification for the songs with the title, artist, year it was released, genre, and album.

--------LocalMusicManager---------

- This class helps us get the location of the songs stored in the internal storage of the android device. By utilizing this, we can create an array of songs from the audio path of the songs in the internal storage. This is how we can navigate to reach local songs.

--------mp3Parser--------

- This class is what allows ID3 to create the identifications. We will use this mp3Parser class to parse the audio files into specific identifications that we want to use for the ID3 such as the title, artist, year it was released, genre, and album.

--------Playlist---------

- This class will populate the playlist with the songs that have been added to the playlist from the playlist selection screen and the playlist contents.

--------PlaylistManager--------

- This class will help the application manage the separate playlists that have been created. If the user creates more than one playlist then they will need to be managed by this class.
