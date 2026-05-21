package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.hellodelivery.MainActivity;
import com.example.hellodelivery.databinding.ActivityLoginBinding;
import com.example.hellodelivery.utils.SessionManager;
import com.example.hellodelivery.viewmodels.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sessionManager = new SessionManager(this);

        binding.loginButton.setOnClickListener(v -> loginUser());
        binding.registerText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser() {
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            binding.emailLayout.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            binding.passwordLayout.setError("Password is required");
            return;
        }

        binding.loginButton.setEnabled(false);
        authViewModel.login(email, password).observe(this, user -> {
            binding.loginButton.setEnabled(true);
            if (user != null) {
                sessionManager.createSession(user);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Login failed. Check credentials.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
