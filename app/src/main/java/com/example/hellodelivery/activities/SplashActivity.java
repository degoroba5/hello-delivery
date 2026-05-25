package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hellodelivery.MainActivity;
import com.example.hellodelivery.R;
import com.example.hellodelivery.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SessionManager sessionManager = new SessionManager(this);

        // Enterprise UX Flow: Ensure we use the Main Looper for the delay
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent;
            if (sessionManager.isFirstTime()) {
                intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            } else {
                // DIRECT TO HOME: Users start as guests (Like Uber Eats/DoorDash)
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            
            // Clear stack to prevent back-button returning to Splash
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
