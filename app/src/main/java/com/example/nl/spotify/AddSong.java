package com.example.nl.spotify;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import kaaes.spotify.webapi.android.SpotifyService;

public class AddSong extends AppCompatActivity {

    Track selectedTrack;
    TracksPager searchedTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    protected void selectTrack(Track selection) {
        selectedTrack = selection;
    }

    protected void searchForTrack(String input) {
        input = input.trim();
        String q = "";
        for (int i=0;i<input.length();i++) {
            if (input.substring(i, i+1) == " ") {
                q += "+";
            } else {
                q += input.substring(i, i+1);
            }
        }

        //searchedTracks = ;
    }

}
