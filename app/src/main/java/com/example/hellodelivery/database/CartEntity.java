package com.example.hellodelivery.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String productId;
    private String productName;
    private String productImageUrl;
    private double price;
    private int quantity;
    private String storeId;

    public CartEntity(String productId, String productName, String productImageUrl, double price, int quantity, String storeId) {
        this.productId = productId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.price = price;
        this.quantity = quantity;
        this.storeId = storeId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getProductImageUrl() { return productImageUrl; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getStoreId() { return storeId; }
}
