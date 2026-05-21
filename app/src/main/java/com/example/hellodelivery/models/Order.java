package com.example.hellodelivery.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Order {
    @SerializedName("_id")
    private String id;
    private String userId;
    private String storeId;
    private List<OrderItem> items;
    private double totalAmount;
    private String status; // Pending, Preparing, Out for Delivery, Delivered, Cancelled
    private String address;
    private String paymentMethod;
    private String createdAt;

    public Order(String id, String userId, String storeId, List<OrderItem> items, double totalAmount, String status, String address, String paymentMethod, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.storeId = storeId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getStoreId() { return storeId; }
    public List<OrderItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public String getAddress() { return address; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getCreatedAt() { return createdAt; }

    public static class OrderItem {
        private String productId;
        private String name;
        private int quantity;
        private double price;

        public OrderItem(String productId, String name, int quantity, double price) {
            this.productId = productId;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        public String getProductId() { return productId; }
        public String getName() { return name; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }
    }
}
