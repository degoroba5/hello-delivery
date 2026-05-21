package com.example.hellodelivery;

import android.app.Application;
import com.example.hellodelivery.network.SocketManager;

public class DeliveryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize global components
        SocketManager.getInstance().connect();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SocketManager.getInstance().disconnect();
    }
}
