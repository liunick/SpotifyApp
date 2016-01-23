package com.example.nl.spotify;

import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.util.Log;

import com.example.nl.spotify.Playlist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * Created by NL on 1/23/2016.
 */



public class HostConnection {

    private Handler mUpdateHandler;
    private HostServer mHostServer;
    private HostClient mHostClient;

    private static final String TAG = "com.example.nl.spotify.HostConnection";

    private Socket mSocket;
    private int mPort = -1;

    public HostConnection(Handler handler) {
        mUpdateHandler = handler;
        mHostServer = new HostServer(handler);

    }

    public void tearDown() {
        mHostServer.tearDown();
        if (mHostClient != null) {
            mHostClient.tearDown();
        }
    }

    public void connectToServer(InetAddress address, int port) {
        mHostClient = new HostClient(address, port);
    }

    public void sendSong(String song) {
        if (mHostClient != null) {
            mHostClient.sendSong(song);
        }
    }

    public int getLocalPort() {
        return mPort;
    }

    public void setLocalPort(int port) {
        mPort = port;
    }

    public synchronized void updateSongs(String song, boolean local) {
        Log.e(TAG, "Updating song: " + song);
        if (local) {
            song = "me";
        } else {
            song = "them: ";
        }

        Bundle songBundle = new Bundle();
        songBundle.putString("song", song);
        Message mSong = new Message();
        mSong.setData(songBundle);
        mUpdateHandler.sendMessage(mSong);
    }

    private synchronized void setSocket(Socket socket) {
        Log.d(TAG, "setSocket is being called");
        if (socket == null) {
            Log.d(TAG, "Setting a null socket");
        }
        if (mSocket != null) {
            if (mSocket.isConnected()) {
                try {
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mSocket = socket;
    }

    private Socket getSocket() {
        return mSocket;
    }

    private class HostServer {
        ServerSocket mServerSocket = null;
        Thread mThread = null;

        public HostServer(Handler handler) {
            mThread = new Thread(new ServerThread());
            mThread.start();
        }

        public void tearDown() {
            mThread.interrupt();
            try {
                mServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Error when closing server socket");
            }
        }

        class ServerThread implements Runnable {

            @Override
            public void run() {
                try {
                    mServerSocket = new ServerSocket(0);
                    setLocalPort(mServerSocket.getLocalPort());

                    while (!Thread.currentThread().isInterrupted()) {
                        Log.d(TAG, "ServerSocket Created, awaiting connection");
                        setSocket(mServerSocket.accept());
                        Log.d(TAG, "Connectted.");
                        if (mHostClient == null) {
                            int port = mSocket.getPort();
                            InetAddress address = mSocket.getInetAddress();
                            connectToServer(address, port);
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error creating ServerSocket: ", e);
                    e.printStackTrace();
                }
            }
        }
    }

    private class HostClient {
        private InetAddress mAddress;
        private int PORT;

        private final String CLIENT_TAG = "HostClient";

        private Thread mSendThread;
        private Thread mRecThread;

        public HostClient(InetAddress address, int port) {
            Log.d(CLIENT_TAG, "Creating chatClient");
            this.mAddress = address;
            this.PORT = port;

            mSendThread = new Thread(new SendingThread());
            mSendThread.start();
        }

        class SendingThread implements Runnable {
            BlockingQueue<String> mSongQueue;
            private int QUEUE_CAPACITY = 10;

            public SendingThread() {
                mSongQueue = new ArrayBlockingQueue<String>(QUEUE_CAPACITY);
            }

            @Override
            public void run() {
                try {
                    if (getSocket() == null) {
                        setSocket(new Socket(mAddress, PORT));
                        Log.d(CLIENT_TAG, "Client-side socket initialized.");
                    } else {
                        Log.d(CLIENT_TAG, "Socket alreayd initialized. Skipping!");
                    }

                    mRecThread = new Thread(new ReceivingThread());
                } catch (UnknownHostException e) {
                    Log.d(CLIENT_TAG, "Initializing socket failed, UHE", e);
                } catch(IOException e) {
                    e.printStackTrace();
                }


                while (true) {
                    try {
                        String msg = mSongQueue.take();
                        sendSong(msg);
                    } catch(InterruptedException e) {
                        Log.d(CLIENT_TAG, "Message sending loop interrupted, exiting");
                    }
                }

            }
        }

        class ReceivingThread implements Runnable {

            @Override
            public void run() {
                BufferedReader input;
                try {
                    input = new BufferedReader(new InputStreamReader(
                            mSocket.getInputStream()));
                    while (!Thread.currentThread().isInterrupted()) {
                        String messageStr = null;
                        messageStr = input.readLine();
                        if (messageStr != null) {
                            Log.d(CLIENT_TAG, "Read from the stream: " + messageStr);
                            updateSongs(messageStr, false);
                        } else {
                            Log.d(CLIENT_TAG, "The nulls! The nulls!");
                            break;
                        }
                    }

                    input.close();
                } catch (IOException e) {
                    Log.e(CLIENT_TAG, "Server loop error: ", e);
                }
            }
        }

        public void tearDown() {
            try {
                getSocket().close();
            } catch(IOException ioe) {
                Log.e(CLIENT_TAG, "Error when closing server socket.");
            }
        }

        public void sendSong (String song) {
            try {
                Socket socket = getSocket();
                if (socket == null) {
                    Log.d(CLIENT_TAG, "Socket is null, wtf?");
                } else if (socket.getOutputStream() == null) {
                    Log.d(CLIENT_TAG, "Socket output stream is null, wtf?");
                }

                PrintWriter out = new PrintWriter(
                        new BufferedWriter(
                                new OutputStreamWriter(getSocket().getOutputStream())), true);
                out.println(song);
                out.flush();
                updateSongs(song, true);
            } catch (UnknownHostException e) {
                Log.d(CLIENT_TAG, "Unknown Host", e);
            } catch (IOException e) {
                Log.d(CLIENT_TAG, "I/O Exception", e);
            } catch (Exception e) {
                Log.d(CLIENT_TAG, "Error3", e);
            }
            Log.d(CLIENT_TAG, "Client sent message: " + song);
        }
    }
}