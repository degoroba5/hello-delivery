package com.example.hellodelivery.models;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("_id")
    private String id;
    private String name;
    private String description;
    private double price;
    private double discountPrice;
    private String imageUrl;
    private String categoryId;
    private String storeId;
    private float rating;
    private int stock;

    public Product(String id, String name, String description, double price, double discountPrice, String imageUrl, String categoryId, String storeId, float rating, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.storeId = storeId;
        this.rating = rating;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public double getDiscountPrice() { return discountPrice; }
    public String getImageUrl() { return imageUrl; }
    public String getCategoryId() { return categoryId; }
    public String getStoreId() { return storeId; }
    public float getRating() { return rating; }
    public int getStock() { return stock; }
}
