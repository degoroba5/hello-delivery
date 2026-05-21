package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hellodelivery.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();

        binding.btnChangePassword.setOnClickListener(v -> {
            // Navigate to Change Password Activity
            Toast.makeText(this, "Feature coming soon", Toast.LENGTH_SHORT).show();
        });

        binding.btnPrivacyPolicy.setOnClickListener(v -> {
            // Open Privacy Policy URL
            Toast.makeText(this, "Opening Privacy Policy...", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
