package com.example.nl.spotify;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.PlayerStateCallback;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;

public class MainActivity extends AppCompatActivity implements PlayerNotificationCallback,
        ConnectionStateCallback, View.OnClickListener{

    public static final String CLIENT_ID = "a6bb6713adee499c94a26d64de4f7e23";
    public static final String REDIRECT_URI = "spotify://callback";

    //public AuthHelper auth;

    public static Player mPlayer;
    private Button bMainAddSong, bMainOpenCon, bMainLogout;
    private static boolean isPlaying;

    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons added - Don't delete from Main
        bMainAddSong = (Button) findViewById(R.id.bMainAddSong);
        bMainOpenCon = (Button) findViewById(R.id.bMainOpenCon);
        bMainLogout = (Button) findViewById(R.id.bMainLogout);

        bMainAddSong.setOnClickListener(this);
        bMainOpenCon.setOnClickListener(this);
        bMainLogout.setOnClickListener(this);

        //auth.AuthRequest();

//        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
//                AuthenticationResponse.Type.TOKEN,
//                REDIRECT_URI);
//        builder.setScopes(new String[]{"user-read-private", "user-library-modify", "streaming"});
//        AuthenticationRequest request = builder.build();
//
//        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);

                Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                    @Override
                    public void onInitialized(Player player) {
                        mPlayer = player;
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addPlayerNotificationCallback(MainActivity.this);
                        mPlayer.play("spotify:track:2TpxZ7JUBn3uw46aR7qd6V");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("MainActivity", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.bMainAddSong:
                startActivity(new Intent(this, AddSong.class));
                break;
            case R.id.bMainOpenCon:
<<<<<<< HEAD
                AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                        AuthenticationResponse.Type.TOKEN,
                        REDIRECT_URI);
                builder.setScopes(new String[]{"user-read-private", "user-library-modify", "streaming"});
                AuthenticationRequest request = builder.build();
                AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
                startActivity(new Intent(this, OpenCon.class));
=======
                if (mPlayer==null) {
                    AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                            AuthenticationResponse.Type.TOKEN,
                            REDIRECT_URI);
                    builder.setScopes(new String[]{"user-read-private", "user-library-modify", "streaming"});
                    AuthenticationRequest request = builder.build();
                    AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
                }
                if (mPlayer!=null)
                    System.out.println("mPlayer is logged in: " + mPlayer.isLoggedIn());
>>>>>>> 8a290c2b46fdfd7d57d50e047f1146370d4d01ef
                break;
            case R.id.bMainLogout:
                if (mPlayer != null) {
                    mPlayer.logout();
                }
                AuthenticationClient.clearCookies(this); // Clears password so that the client realizes the user must login again
                Intent restart = new Intent(this, MainActivity.class); // Restarts the app so that user ends up on login page
                startActivity(restart);
                break;
        }
    }

    public static boolean isPlaying() {
        mPlayer.getPlayerState(new PlayerStateCallback() {
            @Override
            public void onPlayerState(PlayerState playerState) {
                isPlaying = playerState.playing;
            }
        });
        return isPlaying;
    }
}