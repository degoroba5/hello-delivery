package com.example.hellodelivery;

public class DeliveryItem {
    private String id;
    private String name;
    private String description;
    private double price;
    private int imageResId; 
    private String imageUrl; // Added for web images
    private String category;
    private float rating;
    private String deliveryTime;
    private String restaurantName; // Added restaurant name

    public DeliveryItem(String id, String name, String description, double price, int imageResId, String category, float rating, String deliveryTime, String restaurantName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
        this.category = category;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.restaurantName = restaurantName;
    }

    public DeliveryItem(String id, String name, String description, double price, String imageUrl, String category, float rating, String deliveryTime, String restaurantName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.restaurantName = restaurantName;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public String getImageUrl() { return imageUrl; }
    public String getCategory() { return category; }
    public float getRating() { return rating; }
    public String getDeliveryTime() { return deliveryTime; }
    public String getRestaurantName() { return restaurantName; }
}