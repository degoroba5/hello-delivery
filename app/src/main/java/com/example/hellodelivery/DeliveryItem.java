package com.example.hellodelivery;

public class DeliveryItem {
    private String id;
    private String name;
    private String description;
    private double price;
    private int imageResId; // Changed from imageUrl to imageResId for local resources
    private String category;
    private float rating;
    private String deliveryTime;

    public DeliveryItem(String id, String name, String description, double price, int imageResId, String category, float rating, String deliveryTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
        this.category = category;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public String getCategory() { return category; }
    public float getRating() { return rating; }
    public String getDeliveryTime() { return deliveryTime; }
}