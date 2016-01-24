package com.example.nl.spotify;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.net.nsd.NsdServiceInfo;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class OpenCon extends AppCompatActivity {

    static NsdHelp mNsdHelper;

    private Handler mUpdateHandler;
    public static final String TAG = "NsdChat";
    EditText etPlaylistTrack1;
    static HostConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_con);

        Log.d(TAG, "Creating chat activity");

        etPlaylistTrack1 = (EditText) findViewById(R.id.etPlaylistTrack1);

        mUpdateHandler = new Handler() {


            @Override
            public void handleMessage(Message msg) {
                String chatLine = msg.getData().getString("msg");
                addChatLine(chatLine);
            }
        };

    };
    public void clickAdvertise(View v) {
        // Register service
        if(mConnection.getLocalPort() > -1) {
            mNsdHelper.registerService(mConnection.getLocalPort());
        } else {
            Log.d(TAG, "ServerSocket isn't bound.");
        }
    }
    public void clickDiscover(View v) {
        mNsdHelper.discoverServices();
    }
    public static void clickConnect(View v) {
        NsdServiceInfo service = mNsdHelper.getChosenServiceInfo();
        if (service != null) {
            Log.d(TAG, "Connecting.");
            mConnection.connectToServer(service.getHost(),
                    service.getPort());
        } else {
            Log.d(TAG, "No service to connect to!");
        }
    }
    public void clickSend(View v) {
        EditText messageView = null;
        if (AddSong.firstT)
            messageView = (EditText) this.findViewById(R.id.etAddSongResult1);
        else if (AddSong.secondT)
            messageView = (EditText) this.findViewById(R.id.etAddSongResult2);
        else if (AddSong.thirdT)
            messageView = (EditText) this.findViewById(R.id.etAddSongResult3);
        else if (AddSong.fourthT)
            messageView = (EditText) this.findViewById(R.id.etAddSongResult4);
        else if (AddSong.fifthT)
            messageView = (EditText) this.findViewById(R.id.etAddSongResult5);

        if (messageView != null) {
            String messageString = messageView.getText().toString();
            if (!messageString.isEmpty()) {
                mConnection.sendSong(messageString);
            }
            messageView.setText("");
        }
    }
    public void addChatLine(String line) {
        etPlaylistTrack1.append("\n" + line);
    }
    @Override
    protected void onStart() {
        Log.d(TAG, "Starting.");
        mConnection = new HostConnection(mUpdateHandler);
        mNsdHelper = new NsdHelp(this);
        mNsdHelper.initializeNsd();
        super.onStart();
    }
    @Override
    protected void onPause() {
        Log.d(TAG, "Pausing.");
        if (mNsdHelper != null) {
            mNsdHelper.stopDiscovery();
        }
        super.onPause();
    }
    @Override
    protected void onResume() {
        Log.d(TAG, "Resuming.");
        super.onResume();
        if (mNsdHelper != null) {
            mNsdHelper.discoverServices();
        }
    }
    // For KitKat and earlier releases, it is necessary to remove the
    // service registration when the application is stopped.  There's
    // no guarantee that the onDestroy() method will be called (we're
    // killable after onStop() returns) and the NSD service won't remove
    // the registration for us if we're killed.
    // In L and later, NsdService will automatically unregister us when
    // our connection goes away when we're killed, so this step is
    // optional (but recommended).
    @Override
    protected void onStop() {
        Log.d(TAG, "Being stopped.");
        mNsdHelper.tearDown();
        mConnection.tearDown();
        mNsdHelper = null;
        mConnection = null;
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG, "Being destroyed.");
        super.onDestroy();
    }

}
