package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hellodelivery.MainActivity;
import com.example.hellodelivery.databinding.ActivityOtpVerificationBinding;
import com.example.hellodelivery.utils.SessionManager;
import java.util.Locale;
import java.util.Random;

public class OTPVerificationActivity extends AppCompatActivity {

    private ActivityOtpVerificationBinding binding;
    private SessionManager sessionManager;
    private String phoneNumber;
    private String generatedOtp;
    private CountDownTimer countDownTimer;
    private boolean isOtpExpired = false;

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

        // Simulate sending OTP
        sendMockOtp();

        binding.btnVerifyOtp.setOnClickListener(v -> verifyOtp());
        binding.btnResendOtp.setOnClickListener(v -> resendOtp());
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        isOtpExpired = false;
        binding.btnResendOtp.setEnabled(false);
        binding.btnResendOtp.setAlpha(0.5f);

        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "Expires in %02d:%02d", minutes, seconds);
                binding.tvTimer.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                isOtpExpired = true;
                binding.tvTimer.setText("OTP Expired");
                binding.btnResendOtp.setEnabled(true);
                binding.btnResendOtp.setAlpha(1.0f);
            }
        }.start();
    }

    private void sendMockOtp() {
        // Generate a random 6-digit OTP for testing
        generatedOtp = String.valueOf(100000 + new Random().nextInt(900000));
        
        // Simulate network delay then show the OTP in a Toast
        new Handler().postDelayed(() -> {
            Toast.makeText(this, "Development Mode: Your OTP is " + generatedOtp, Toast.LENGTH_LONG).show();
            startTimer();
        }, 1000);
    }

    private void verifyOtp() {
        if (isOtpExpired) {
            binding.otpLayout.setError("OTP has expired. Please resend.");
            return;
        }

        String enteredOtp = binding.otpEditText.getText().toString().trim();

        if (TextUtils.isEmpty(enteredOtp) || enteredOtp.length() < 6) {
            binding.otpLayout.setError("Enter valid 6-digit OTP");
            return;
        }

        if (enteredOtp.equals(generatedOtp)) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            Toast.makeText(this, "Verification Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(OTPVerificationActivity.this, MainActivity.class));
            finishAffinity();
        } else {
            binding.otpLayout.setError("Invalid OTP. Try again.");
        }
    }

    private void resendOtp() {
        binding.otpEditText.setText("");
        binding.otpLayout.setError(null);
        sendMockOtp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
