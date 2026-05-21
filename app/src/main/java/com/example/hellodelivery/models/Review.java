package com.example.hellodelivery.models;

public class Review {
    private String id;
    private String userId;
    private String userName;
    private String userImageUrl;
    private float rating;
    private String comment;
    private String date;

    public Review(String id, String userId, String userName, String userImageUrl, float rating, String comment, String date) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userImageUrl = userImageUrl;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserImageUrl() { return userImageUrl; }
    public float getRating() { return rating; }
    public String getComment() { return comment; }
    public String getDate() { return date; }
}
