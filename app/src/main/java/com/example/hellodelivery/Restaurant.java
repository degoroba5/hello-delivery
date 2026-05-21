package com.example.hellodelivery;

public class Restaurant {
    private String name;
    private String imageUrl;
    private float rating;
    private String deliveryTime;

    public Restaurant(String name, String imageUrl, float rating, String deliveryTime) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
    }

    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public float getRating() { return rating; }
    public String getDeliveryTime() { return deliveryTime; }
}