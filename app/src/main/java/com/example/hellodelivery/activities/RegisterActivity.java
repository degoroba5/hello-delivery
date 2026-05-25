package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.hellodelivery.databinding.ActivityRegisterBinding;
import com.example.hellodelivery.models.User;
import com.example.hellodelivery.utils.Constants;
import com.example.hellodelivery.utils.SessionManager;
import com.example.hellodelivery.viewmodels.AuthViewModel;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private AuthViewModel authViewModel;
    private SessionManager sessionManager;
    private boolean isFromCheckout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sessionManager = new SessionManager(this);
        
        isFromCheckout = getIntent().getBooleanExtra("from_checkout", false);

        setupTextWatchers();

        binding.registerButton.setOnClickListener(v -> registerUser());
        binding.loginText.setOnClickListener(v -> finish());
    }

    private void setupTextWatchers() {
        TextWatcher commonWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.nameLayout.setError(null);
                binding.emailLayout.setError(null);
                binding.phoneLayout.setError(null);
                binding.passwordLayout.setError(null);
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        binding.nameEditText.addTextChangedListener(commonWatcher);
        binding.emailEditText.addTextChangedListener(commonWatcher);
        binding.phoneEditText.addTextChangedListener(commonWatcher);
        binding.passwordEditText.addTextChangedListener(commonWatcher);
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
                // Success: Navigate to OTP screen
                Intent intent = new Intent(RegisterActivity.this, OTPVerificationActivity.class);
                intent.putExtra(Constants.EXTRA_PHONE_NUMBER, phone);
                intent.putExtra("from_checkout", isFromCheckout);
                // Pass user object to OTP activity to create session after verification
                intent.putExtra("user_data", new Gson().toJson(user));
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Registration failed. Please check your connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
