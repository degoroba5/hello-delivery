package com.example.hellodelivery.models;

public class Banner {
    private String id;
    private String imageUrl;
    private String actionUrl;

    public Banner(String id, String imageUrl, String actionUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.actionUrl = actionUrl;
    }

    public String getId() { return id; }
    public String getImageUrl() { return imageUrl; }
    public String getActionUrl() { return actionUrl; }
}
