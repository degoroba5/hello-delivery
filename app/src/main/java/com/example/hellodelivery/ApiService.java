package com.example.hellodelivery;

import java.util.List;

public interface ApiService {
    interface DataCallback<T> {
        void onSuccess(T data);
        void onError(String error);
    }

    void getItemsByCategory(String category, DataCallback<List<DeliveryItem>> callback);
    void getPopularItems(DataCallback<List<DeliveryItem>> callback);
    void getRecommendedItems(DataCallback<List<DeliveryItem>> callback);
    void searchItems(String query, DataCallback<List<DeliveryItem>> callback);
}