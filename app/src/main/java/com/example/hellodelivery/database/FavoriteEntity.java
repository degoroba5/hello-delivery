package com.example.hellodelivery.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String productId;
    private String productName;
    private String imageUrl;
    private double price;

    public FavoriteEntity(String productId, String productName, String imageUrl, double price) {
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getImageUrl() { return imageUrl; }
    public double getPrice() { return price; }
}
