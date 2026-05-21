package com.example.hellodelivery.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "addresses")
public class AddressEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String addressTitle; // Home, Office, etc.
    private String fullAddress;
    private double latitude;
    private double longitude;

    public AddressEntity(String addressTitle, String fullAddress, double latitude, double longitude) {
        this.addressTitle = addressTitle;
        this.fullAddress = fullAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAddressTitle() { return addressTitle; }
    public String getFullAddress() { return fullAddress; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
