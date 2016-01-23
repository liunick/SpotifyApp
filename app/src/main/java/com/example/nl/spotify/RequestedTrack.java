package com.example.nl.spotify;

/**
 * Created by alexmaynard on 1/23/16.
 */

public class RequestedTrack {

    private String title;
    private String artist;
    private String album;

    public RequestedTrack(String title, String artist, String album) {
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public String getTitle() { return title; }

    public String getArtist() { return artist; }

    public String getAlbum() { return album; }

}
