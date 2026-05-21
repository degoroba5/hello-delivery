package com.example.hellodelivery.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    private String id;
    private String name;
    private String email;
    private String phone;
    private String token;

    public User(String id, String name, String email, String phone, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.token = token;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getToken() { return token; }
}
