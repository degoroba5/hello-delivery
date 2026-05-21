package com.example.hellodelivery;

public interface PaymentService {
    interface PaymentCallback {
        void onPaymentSuccess(String transactionId);
        default void onPaymentError(String error) {}
    }

    void processPayment(double amount, String paymentMethod, PaymentCallback callback);
}