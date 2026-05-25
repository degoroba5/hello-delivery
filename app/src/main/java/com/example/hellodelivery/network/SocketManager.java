package com.example.hellodelivery.network;

import android.util.Log;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;

public class SocketManager {
    private static final String TAG = "SocketManager";
    private static final String SOCKET_URL = "https://api.example-delivery.com/";
    private static SocketManager instance;
    private Socket mSocket;

    private SocketManager() {
        try {
            // Using a try-catch and logging to prevent startup crashes if URL is invalid
            mSocket = IO.socket(SOCKET_URL);
        } catch (URISyntaxException e) {
            Log.e(TAG, "Socket initialization failed: " + e.getMessage());
        }
    }

    public static synchronized SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
        }
        return instance;
    }

    public Socket getSocket() {
        return mSocket;
    }

    public void connect() {
        if (mSocket != null && !mSocket.connected()) {
            mSocket.connect();
            Log.d(TAG, "Attempting to connect to socket...");
        }
    }

    public void disconnect() {
        if (mSocket != null && mSocket.connected()) {
            mSocket.disconnect();
            Log.d(TAG, "Socket disconnected.");
        }
    }
}
