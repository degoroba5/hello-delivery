package com.example.hellodelivery.models;

import com.google.gson.annotations.SerializedName;

public class Store {
    @SerializedName("_id")
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private float rating;
    private String deliveryTime;
    private double deliveryFee;
    private String address;
    private boolean isOpen;
    private String category; // Added for filtering

    public Store(String id, String name, String description, String imageUrl, float rating, String deliveryTime, double deliveryFee, String address, boolean isOpen, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.deliveryFee = deliveryFee;
        this.address = address;
        this.isOpen = isOpen;
        this.category = category;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public float getRating() { return rating; }
    public String getDeliveryTime() { return deliveryTime; }
    public double getDeliveryFee() { return deliveryFee; }
    public String getAddress() { return address; }
    public boolean isOpen() { return isOpen; }
    public String getCategory() { return category; }
}
