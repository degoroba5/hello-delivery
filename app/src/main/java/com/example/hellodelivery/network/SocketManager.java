package com.example.hellodelivery.network;

import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;

public class SocketManager {
    private static final String SOCKET_URL = "https://api.example-delivery.com/";
    private static SocketManager instance;
    private Socket mSocket;

    private SocketManager() {
        try {
            mSocket = IO.socket(SOCKET_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
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
        }
    }

    public void disconnect() {
        if (mSocket != null && mSocket.connected()) {
            mSocket.disconnect();
        }
    }
}
