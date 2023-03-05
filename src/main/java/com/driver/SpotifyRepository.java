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
        for(Song a : songs){
            if(a.getLength()==length){
                List<Song> thisLengthSongs  = new ArrayList<>();
                thisLengthSongs.add(a);
                Playlist playlist = new Playlist(title);
                playlistSongMap.put(playlist,thisLengthSongs);
                return playlist;
            }
        }
        return null;
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
        return thisNamePlayList;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        return null;
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
