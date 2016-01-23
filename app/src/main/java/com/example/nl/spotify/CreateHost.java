package com.example.nl.spotify;

import android.content.Context;
import android.net.nsd.NsdManager;

import java.io.IOException;
import java.net.ServerSocket;
import android.net.nsd.NsdServiceInfo;

/**
 * Created by anton on 1/23/16.
 */
public class CreateHost extends RegistrationListenerClass {

    int mLocalPort = 0;
    NsdManager.RegistrationListener mRegistrationListener = new RegistrationListenerClass();
    NsdServiceInfo serviceInfo = new NsdServiceInfo();
    RegistrationListenerClass mNsdManager = Context.getSystemService(Context.NSD_SERVICE);
    String mServiceName = "";



    public void initializeServerSocket() throws IOException {
        // Initialize a server socket on the next available port.
        ServerSocket mServerSocket = new ServerSocket(0);

        // Store the chosen port.
        mLocalPort =  mServerSocket.getLocalPort();




    }

    public void registerService(int port) {
        // Create the NsdServiceInfo object, and populate it.
        //NsdServiceInfo serviceInfo  = new NsdServiceInfo();

        // The name is subject to change based on conflicts
        // with other services advertised on the same network.
        serviceInfo.setServiceName("SpotifyVoter");
        serviceInfo.setServiceType("_http._tcp.");
        serviceInfo.setPort(port);

        mNsdManager.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
    }



    public void initializeRegistrationListener() {
        mRegistrationListener = new NsdManager.RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                // Save the service name.  Android may have changed it in order to
                // resolve a conflict, so update the name you initially requested
                // with the name Android actually used.
                mServiceName = NsdServiceInfo.getServiceName();
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed!  Put debugging code here to determine why.
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
                // Service has been unregistered.  This only happens when you call
                // NsdManager.unregisterService() and pass in this listener.
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Unregistration failed.  Put debugging code here to determine why.
            }
        };
    }


}
