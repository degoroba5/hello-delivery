package com.example.hellodelivery;

import android.app.Application;
import android.util.Log;
import com.example.hellodelivery.network.SocketManager;

public class DeliveryApplication extends Application {

    private static final String TAG = "DeliveryApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        
        try {
            // Initialize global components safely
            SocketManager.getInstance().connect();
            Log.d(TAG, "Application initialized and services started.");
        } catch (Exception e) {
            Log.e(TAG, "Global service initialization failed: " + e.getMessage());
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        try {
            SocketManager.getInstance().disconnect();
        } catch (Exception ignored) {}
    }
}
