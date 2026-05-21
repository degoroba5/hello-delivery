package com.example.hellodelivery;

import java.util.List;

public class Order {
    private String id;
    private List<DeliveryItem> items;
    private double totalAmount;
    private String date;

    public Order(String id, List<DeliveryItem> items, double totalAmount, String date) {
        this.id = id;
        this.items = items;
        this.totalAmount = totalAmount;
        this.date = date;
    }

    public String getId() { return id; }
    public List<DeliveryItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getDate() { return date; }
}