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
            etPlaylistTrack4, etPlaylistTrack5;

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

        etPlaylistTrack1 = (EditText) findViewById(R.id.etPlaylistTrack1);
        etPlaylistTrack2 = (EditText) findViewById(R.id.etPlaylistTrack2);
        etPlaylistTrack3 = (EditText) findViewById(R.id.etPlaylistTrack3);
        etPlaylistTrack4 = (EditText) findViewById(R.id.etPlaylistTrack4);
        etPlaylistTrack5 = (EditText) findViewById(R.id.etPlaylistTrack5);

        //if (!MainActivity.mPlayer.playing())

    }

}
