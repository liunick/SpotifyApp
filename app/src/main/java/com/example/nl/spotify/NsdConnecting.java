package com.example.nl.spotify;

import android.net.nsd.*;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

/**
 * Created by anton on 1/23/16.
 */
public class NsdConnecting {
    public void initializeDiscoveryListener() {
        myRegistrationListener = new android.net.nsd.NsdManager.RegistrationListener() {

            @Override
            public void onDiscoveryStarted(String serviceType) {
                // Save the service name.  Android may have changed it in order to
                // resolve a conflict, so update the name you initially requested
                // with the name Android actually used.
                myServiceName = NsdServiceInfo.getServiceName();
                Log.d(TAG, "Registered " + "myServiceName");
            }

            @Override
            public void onRegistrationFailed(android.net.nsd.NsdServiceInfo serviceInfo, int errorCode) {
                // Registration failed!  Put debugging code here to determine why.
                Log.d(TAG, "Registration FAILED " + errorCode);
            }

            @Override
            public void onServiceUnregistered(android.net.nsd.NsdServiceInfo arg0) {
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
