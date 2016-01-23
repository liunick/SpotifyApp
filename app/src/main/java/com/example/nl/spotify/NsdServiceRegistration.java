package com.example.nl.spotify;
import android.content.Context;
import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by anton on 1/23/16.
 */
public class NsdServiceRegistration extends NsdManager {
    Context context;
    NsdServiceInfo myNsdServiceInfo;
    NsdManager myNsdManager;
    NsdManager.RegistrationListener myRegistrationListener;
    ServerSocket myServerSocket;

    String myServiceName = "SpotifyVoter";
    int myLocalPort;
    private static final String TAG = "Service Registration";



    public void registerService(int port) {
        // Create the NsdServiceInfo object, and populate it.
        NsdServiceInfo serviceInfo  = new NsdServiceInfo();

        // The name is subject to change based on conflicts
        // with other services advertised on the same network.
        serviceInfo.setServiceName("SpotifyVoter");
        serviceInfo.setServiceType("_http._tcp.");
        serviceInfo.setPort(port);

        myNsdManager.registerService(myNsdServiceInfo, NsdManager.PROTOCOL_DNS_SD, myRegistrationListener);
    }

    public void initializeServerSocket() throws IOException {
        // Initialize a server socket on the next available port.
        myServerSocket = new ServerSocket(0);

        // Store the chosen port.
        myLocalPort = myServerSocket.getLocalPort();

    }

    public void initializeRegistrationListener() {
        myRegistrationListener = new NsdManager.RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                // Save the service name.  Android may have changed it in order to
                // resolve a conflict, so update the name you initially requested
                // with the name Android actually used.
                myServiceName = NsdServiceInfo.getServiceName();
                Log.d(TAG, "Registered " + myServiceName);
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed!  Put debugging code here to determine why.
                Log.d(TAG, "Registration FAILED " + errorCode);
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                // Service has been unregistered.  This only happens when you call
                // NsdManager.unregisterService() and pass in this listener.
                Log.d(TAG, "Unregistered " + arg0);
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Unregistration failed.  Put debugging code here to determine why.
                Log.d(TAG,"Unregistration FAILED " + errorCode);
            }
        };
    }


}