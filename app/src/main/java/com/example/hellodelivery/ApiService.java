package com.example.hellodelivery;

import java.util.List;

public interface ApiService {
    interface DataCallback<T> {
        void onSuccess(T data);
        default void onError(String error) {}
    }

    void getItemsByCategory(String category, DataCallback<List<DeliveryItem>> callback);
    void getPopularItems(DataCallback<List<DeliveryItem>> callback);
    void getRecommendedItems(DataCallback<List<DeliveryItem>> callback);
    void getBestReviewedItems(DataCallback<List<DeliveryItem>> callback);
    void searchItems(String query, DataCallback<List<DeliveryItem>> callback);
    void getOrderHistory(DataCallback<List<Order>> callback);
    void getRestaurants(DataCallback<List<Restaurant>> callback);
    void getItemsByRestaurant(String restaurantName, DataCallback<List<DeliveryItem>> callback);
}