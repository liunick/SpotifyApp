package com.example.nl.spotify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.view.View;
import com.spotify.sdk.android.player.PlayerState;

import java.util.ArrayList;

/**
 * Created by alexmaynard on 1/23/16.
 */
public class Playlist extends AppCompatActivity {

    EditText etPlaylistTrack1, etPlaylistTrack2, etPlaylistTrack3,
            etPlaylistTrack4, etPlaylistTrack5, etPlaylistTrack6, etPlaylistTrack7,
            etPlaylistTrack8, etPlaylistTrack9, etPlaylistTrack10;
    ;

    private ArrayList<RequestedTrack> playlist = new ArrayList<>();

    public void addTrack(RequestedTrack track) {
        playlist.add(track);
    }

    public void deleteTrack(RequestedTrack track) {
        playlist.remove(track);
    }

    public void deleteTrack(int index) {
        playlist.remove(index);
    }

    public void clearPlaylist() {
        playlist.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etPlaylistTrack1.setText(playlist.get(0).getTitle() + " - " + playlist.get(0).getArtist()
                + "\n" + playlist.get(0).getAlbum());
        etPlaylistTrack2.setText(playlist.get(1).getTitle() + " - " + playlist.get(1).getArtist()
                + "\n" + playlist.get(1).getAlbum());
        etPlaylistTrack3.setText(playlist.get(2).getTitle() + " - " + playlist.get(2).getArtist()
                + "\n" + playlist.get(2).getAlbum());
        etPlaylistTrack4.setText(playlist.get(3).getTitle() + " - " + playlist.get(3).getArtist()
                + "\n" + playlist.get(3).getAlbum());
        etPlaylistTrack5.setText(playlist.get(4).getTitle() + " - " + playlist.get(4).getArtist()
                + "\n" + playlist.get(4).getAlbum());
        etPlaylistTrack6.setText(playlist.get(5).getTitle() + " - " + playlist.get(5).getArtist()
                + "\n" + playlist.get(5).getAlbum());
        etPlaylistTrack7.setText(playlist.get(6).getTitle() + " - " + playlist.get(6).getArtist()
                + "\n" + playlist.get(6).getAlbum());
        etPlaylistTrack8.setText(playlist.get(7).getTitle() + " - " + playlist.get(7).getArtist()
                + "\n" + playlist.get(7).getAlbum());
        etPlaylistTrack9.setText(playlist.get(8).getTitle() + " - " + playlist.get(8).getArtist()
                + "\n" + playlist.get(8).getAlbum());
        etPlaylistTrack10.setText(playlist.get(9).getTitle() + " - " + playlist.get(9).getArtist()
                + "\n" + playlist.get(9).getAlbum());

        etPlaylistTrack1 = (EditText) findViewById(R.id.etPlaylistTrack1);
        etPlaylistTrack2 = (EditText) findViewById(R.id.etPlaylistTrack2);
        etPlaylistTrack3 = (EditText) findViewById(R.id.etPlaylistTrack3);
        etPlaylistTrack4 = (EditText) findViewById(R.id.etPlaylistTrack4);
        etPlaylistTrack5 = (EditText) findViewById(R.id.etPlaylistTrack5);
        etPlaylistTrack6 = (EditText) findViewById(R.id.etPlaylistTrack6);
        etPlaylistTrack7 = (EditText) findViewById(R.id.etPlaylistTrack7);
        etPlaylistTrack8 = (EditText) findViewById(R.id.etPlaylistTrack8);
        etPlaylistTrack9 = (EditText) findViewById(R.id.etPlaylistTrack9);
        etPlaylistTrack10 = (EditText) findViewById(R.id.etPlaylistTrack10);

        //if (!MainActivity.mPlayer.playing())

    }

}
