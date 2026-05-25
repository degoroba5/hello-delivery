package com.example.hellodelivery.models;

import com.google.gson.annotations.SerializedName;

public class TrendingFood {
    @SerializedName("_id")
    private String id;
    private String name;
    private String restaurantName;
    private String storeId;
    private String categoryName; // Added for filtering
    private String imageUrl;
    private double price;
    private double discountPrice;
    private float rating;
    private String deliveryTime;
    private boolean isFavorite;
    private boolean isTrending;

    public TrendingFood(String id, String name, String restaurantName, String storeId, String categoryName, String imageUrl, double price, double discountPrice, float rating, String deliveryTime, boolean isFavorite, boolean isTrending) {
        this.id = id;
        this.name = name;
        this.restaurantName = restaurantName;
        this.storeId = storeId;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.discountPrice = discountPrice;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.isFavorite = isFavorite;
        this.isTrending = isTrending;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getRestaurantName() { return restaurantName; }
    public String getStoreId() { return storeId; }
    public String getCategoryName() { return categoryName; }
    public String getImageUrl() { return imageUrl; }
    public double getPrice() { return price; }
    public double getDiscountPrice() { return discountPrice; }
    public float getRating() { return rating; }
    public String getDeliveryTime() { return deliveryTime; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    public boolean isTrending() { return isTrending; }
}
