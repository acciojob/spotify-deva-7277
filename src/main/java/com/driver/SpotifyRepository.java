package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }
    // SpotifyService spotifyService=new SpotifyService();
    public User createUser(String name, String mobile) {
        User user = new User(name,mobile);
        users.add(user);
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist = new Artist(name);
        artists.add(artist);
        return artist;
    }

    public Album createAlbum(String title, String artistName) {

        for(Artist u: artists) {

            if (u.getName().equalsIgnoreCase(artistName)) {
                Album album = new Album(title);
                albums.add(album);
                List<Album> newList=new ArrayList<>();
                newList.add(album);
                artistAlbumMap.put(u, newList);
                return album;
            }
        }
            Artist art=createArtist(artistName);
            Album album = new Album(title);
            albums.add(album);
            artistAlbumMap.put(art, albums);
            return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
        Song song = new Song(title,length);
        Boolean flag=true;
        for(Album a : albums){
            if(a.getTitle().equalsIgnoreCase(albumName)){
                songs.add(song);
                albumSongMap.put(a,songs);
                flag=false;
            }
        }
        if(flag)
        {
            throw new Exception("Album not present");
        }
        return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        Playlist playlist = new Playlist(title);
        List<Song> thisLengthSongs  = new ArrayList<>();
        for(Song a : songs){
            if(a.getLength()==length){
                thisLengthSongs.add(a);
            }
        }
        playlistSongMap.put(playlist,thisLengthSongs);
        List<User> use=new ArrayList<>();
        User user=null;
        for(User u:users)
        {
            if(u.getMobile().equalsIgnoreCase(mobile))
            {
                use.add(u);
                user=u;
            }
        }
        playlistListenerMap.put(playlist,use);
        creatorPlaylistMap.put(user,playlist);
        if(userPlaylistMap.containsKey(user))
        {
            List<Playlist> newPlayList=userPlaylistMap.get(user);
            newPlayList.add(playlist);
            userPlaylistMap.put(user,newPlayList);
        }
        else {
            List<Playlist> newPlayList=new ArrayList<>();
            newPlayList.add(playlist);
            userPlaylistMap.put(user,newPlayList);
        }

        return playlist;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        List<Song> thisNameSongs = new ArrayList<>();
        Playlist thisNamePlayList = new Playlist(title);
        for(String t: songTitles) {
            for (Song s : songs) {
                if(t.equalsIgnoreCase(s.getTitle())){
                    thisNameSongs.add(s);
                }
            }
        }
        playlistSongMap.put(thisNamePlayList,thisNameSongs);
        for(User u:users)
        {
            if(u.getMobile().equalsIgnoreCase(mobile))
            {
                List<User> use=new ArrayList<>();
                use.add(u);
                playlistListenerMap.put(thisNamePlayList,use);
            }
        }
        return thisNamePlayList;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        User user=null;
        for(User u:users)
        {
            if(u.getMobile().equalsIgnoreCase(mobile))
            {
                user=u;
            }
        }
        if(user==null)
        {
            throw new Exception("user not exist");
        }
        Playlist play=null;
        for(Playlist p:playlists)
        {
            if(p.getTitle().equalsIgnoreCase(playlistTitle))
            {
                play=p;
            }
        }
        if(play==null){
            throw new Exception("user not exist");
        }
        List<User> userList = playlistListenerMap.get(play);
        for(User u: userList) {
            if (u.equals(user))
                System.out.println("Do nothing");
            else {
                userList.add(user);
                playlistListenerMap.put(play, userList);
            }
        }

        return play;
    }

    public Song likeSong(String mobile, String songTitle) throws Exception {
        return null;
    }

    public String mostPopularArtist() {
        return null;
    }

    public String mostPopularSong() {
        return null;
   }
}
