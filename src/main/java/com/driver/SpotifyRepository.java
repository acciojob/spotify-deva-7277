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
    public SpotifyService spotifyService;
    public User createUser(String name, String mobile) {
        User user = new User(name,mobile);
        users.add(user);
        for(User u: users){
            System.out.println(u);
        }
        return user;
    }

    public Artist createArtist(String name) {
        Artist artist = new Artist(name);
        artists.add(artist);
        for(Artist u: artists){
            System.out.println(u);
        }
        return artist;
    }

    public Artist getArtistByName(String artistName){
        for(Artist a: artists){
            if(a.getName().equals(artistName)) return a;
        }
        return null;
    }
    public Album createAlbum(String title, String artistName) {
        Album album = new Album(title);
        if(artists.contains(artistName)){
            albums.add(album);
            Artist artist = getArtistByName(artistName);
            List<Album> thisArtistAlbums =  artistAlbumMap.get(artist.getName());
            thisArtistAlbums.add(album);
            for(Album thisAlbum : thisArtistAlbums){
                System.out.println(thisAlbum);
            }
            artistAlbumMap.put(artist,thisArtistAlbums);
        }
        else{
            spotifyService.createArtist(artistName);
            albums.add(album);
            Artist artist = getArtistByName(artistName);
            List<Album> thisArtistAlbums =  artistAlbumMap.get(artist.getName());
            thisArtistAlbums.add(album);
            for(Album thisAlbum : thisArtistAlbums){
                System.out.println(thisAlbum);
            }
            artistAlbumMap.put(artist,thisArtistAlbums);
        }
        for(Artist a: artistAlbumMap.keySet()){
            List<Album> thisArtistAlbum = artistAlbumMap.get(a);
            if(thisArtistAlbum!=null){
                for(Album album1 : thisArtistAlbum){
                    System.out.println(album1);
                }
            }
        }
        return album;
    }

    public Album getAlbumByName(String albumName){
        for(Album a: albums){
            if(a.getTitle().equals(albumName)) return a;
        }
        return null;
    }
    public Song createSong(String title, String albumName, int length) throws Exception{
        Song song = new Song(title,length);
        if(albums.contains(albumName)){
            songs.add(song);
            Album album = getAlbumByName(albumName);
            List<Song> thisAlbumSongs = albumSongMap.get(album.getTitle());
            thisAlbumSongs.add(song);
        }
        else{
            throw new Exception("Album not present");
        }
        return song;
    }

    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
        return null;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {
        return null;
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
