package com.example.hellodelivery.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hellodelivery.databinding.ActivityEditProfileBinding;
import com.example.hellodelivery.models.User;
import com.example.hellodelivery.utils.SessionManager;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        setupToolbar();
        loadUserData();

        binding.btnSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void loadUserData() {
        User user = sessionManager.getUserDetails();
        if (user != null) {
            binding.etEditName.setText(user.getName());
            binding.etEditEmail.setText(user.getEmail());
            binding.etEditPhone.setText(user.getPhone());
        }
    }

    private void saveProfile() {
        String name = binding.etEditName.getText().toString().trim();
        String phone = binding.etEditPhone.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // In a real app, call API to update profile
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}
