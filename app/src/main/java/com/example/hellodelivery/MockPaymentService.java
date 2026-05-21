package com.example.hellodelivery;

import android.os.Handler;
import android.os.Looper;
import java.util.UUID;

public class MockPaymentService implements PaymentService {
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void processPayment(double amount, String paymentMethod, PaymentCallback callback) {
        // Simulate payment processing delay
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 2 seconds delay
                if (amount > 0) {
                    String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                    mainHandler.post(() -> callback.onPaymentSuccess(transactionId));
                } else {
                    mainHandler.post(() -> callback.onPaymentError("Invalid amount: " + amount));
                }
            } catch (InterruptedException e) {
                mainHandler.post(() -> callback.onPaymentError(e.getMessage()));
            }
        }).start();
    }
}