package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hellodelivery.MainActivity;
import com.example.hellodelivery.databinding.ActivityOtpVerificationBinding;
import com.example.hellodelivery.utils.SessionManager;

public class OTPVerificationActivity extends AppCompatActivity {

    private ActivityOtpVerificationBinding binding;
    private SessionManager sessionManager;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        phoneNumber = getIntent().getStringExtra("phone_number");

        if (phoneNumber != null) {
            binding.tvOtpDescription.setText("Enter the 6-digit code sent to " + phoneNumber);
        }

        binding.btnVerifyOtp.setOnClickListener(v -> verifyOtp());
        binding.btnResendOtp.setOnClickListener(v -> resendOtp());
    }

    private void verifyOtp() {
        String otp = binding.otpEditText.getText().toString().trim();

        if (TextUtils.isEmpty(otp) || otp.length() < 6) {
            binding.otpLayout.setError("Enter valid 6-digit OTP");
            return;
        }

        // In a real enterprise app, you would verify this using Firebase Auth
        // or send it to your Node.js backend to verify with a provider like Twilio/Msg91
        
        Toast.makeText(this, "Verification Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(OTPVerificationActivity.this, MainActivity.class));
        finishAffinity();
    }

    private void resendOtp() {
        Toast.makeText(this, "OTP Resent", Toast.LENGTH_SHORT).show();
    }
}
