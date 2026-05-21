package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

        new Handler().postDelayed(() -> {
            if (sessionManager.isFirstTime()) {
                startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
            } else if (sessionManager.isLoggedIn()) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        }, 2000);
    }
}
