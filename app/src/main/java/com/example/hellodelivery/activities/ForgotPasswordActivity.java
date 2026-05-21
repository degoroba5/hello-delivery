package com.example.hellodelivery.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hellodelivery.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnResetPassword.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                binding.emailLayout.setError("Email is required");
                return;
            }
            // In a real app, call API to send reset link
            Toast.makeText(this, "Reset link sent to " + email, Toast.LENGTH_SHORT).show();
            finish();
        });

        binding.btnBackToLogin.setOnClickListener(v -> finish());
    }
}
