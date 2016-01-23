package com.example.nl.spotify;

import android.net.nsd.*;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;
import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager;

import java.net.InetAddress;

/**
 * Created by anton on 1/23/16.
 */
public class NsdConnecting {

    private static final String TAG = "Connection";
    NsdManager.DiscoveryListener myDiscoveryListener;
    NsdManager myNsdManager;
    NsdManager.ResolveListener myResolveListener;
    String myServiceName = "SpotifyVoter";
    NsdServiceInfo mService;
    String SERVICE_TYPE = "_http._tcp.";



    public void initializeDiscoveryListener() {

        // Instantiate a new DiscoveryListener
        myDiscoveryListener = new NsdManager.DiscoveryListener() {

            //  Called as soon as service discovery begins.
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                // A service was found!  Do something with it.
                Log.d(TAG, "Service discovery success" + service);
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    // Service type is the string containing the protocol and
                    // transport layer for this service.
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                } else if (service.getServiceName().equals(myServiceName)) {
                    // The name of the service tells the user what they'd be
                    // connecting to. It could be "Bob's Chat App".
                    Log.d(TAG, "Same machine: " + myServiceName);
                } else if (service.getServiceName().contains("SpotifyVoter")){
                    myNsdManager.resolveService(service, myResolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                // When the network service is no longer available.
                // Internal bookkeeping code goes here.
                Log.e(TAG, "service lost" + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                myNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                myNsdManager.stopServiceDiscovery(this);
            }
        };
    }

    public void initializeResolveListener() {
        myResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails.  Use the error code to debug.
                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

                if (serviceInfo.getServiceName().equals(myServiceName)) {
                    Log.d(TAG, "Same IP.");
                    return;
                }
                mService = serviceInfo;
                int port = mService.getPort();
                InetAddress host = mService.getHost();
            }
        };
    }
}
