package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.hellodelivery.databinding.ActivityRegisterBinding;
import com.example.hellodelivery.utils.Constants;
import com.example.hellodelivery.utils.SessionManager;
import com.example.hellodelivery.viewmodels.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private AuthViewModel authViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sessionManager = new SessionManager(this);

        binding.registerButton.setOnClickListener(v -> registerUser());
        binding.loginText.setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String name = binding.nameEditText.getText().toString().trim();
        String email = binding.emailEditText.getText().toString().trim();
        String phone = binding.phoneEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            binding.nameLayout.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            binding.emailLayout.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            binding.phoneLayout.setError("Phone is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            binding.passwordLayout.setError("Password is required");
            return;
        }

        binding.registerButton.setEnabled(false);
        authViewModel.register(name, email, phone, password).observe(this, user -> {
            binding.registerButton.setEnabled(true);
            if (user != null) {
                // In enterprise apps, usually we go to OTP verification after registration
                Intent intent = new Intent(RegisterActivity.this, OTPVerificationActivity.class);
                intent.putExtra(Constants.EXTRA_PHONE_NUMBER, phone);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
