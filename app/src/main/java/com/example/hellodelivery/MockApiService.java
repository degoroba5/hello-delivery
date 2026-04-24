package com.example.hellodelivery;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockApiService implements ApiService {
    private final List<DeliveryItem> allItems = new ArrayList<>();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public MockApiService() {
        // Food Items
        allItems.add(new DeliveryItem("1", "Classic Burger", "Juicy beef patty with cheese", 8.99, R.drawable.ic_burger, "food", 4.5f, "20-30 min"));
        allItems.add(new DeliveryItem("2", "Pepperoni Pizza", "Large pizza with spicy pepperoni", 12.50, R.drawable.ic_pizza, "food", 4.8f, "30-45 min"));
        allItems.add(new DeliveryItem("5", "Sushi Set", "12 piece assorted sushi", 18.00, R.drawable.ic_sushi, "food", 4.6f, "40-50 min"));
        
        // Grocery Items
        allItems.add(new DeliveryItem("3", "Fresh Milk", "1 Gallon Whole Milk", 4.20, R.drawable.ic_milk, "grocery", 4.9f, "15-20 min"));
        allItems.add(new DeliveryItem("6", "Organic Eggs", "One dozen farm fresh eggs", 5.50, R.drawable.ic_grocery, "grocery", 4.8f, "15-25 min"));
        allItems.add(new DeliveryItem("9", "Fresh Apples", "1kg Red Gala Apples", 3.99, R.drawable.ic_grocery, "grocery", 4.7f, "10-15 min"));
        
        // Pharmacy Items
        allItems.add(new DeliveryItem("4", "Pain Relief", "Extra strength ibuprofen", 6.99, R.drawable.ic_medicine, "pharmacy", 4.7f, "10-15 min"));
        allItems.add(new DeliveryItem("7", "Vitamin C", "1000mg Immune Support", 12.99, R.drawable.ic_medicine, "pharmacy", 4.6f, "10-20 min"));
        allItems.add(new DeliveryItem("8", "First Aid Kit", "Emergency medical supplies", 25.00, R.drawable.ic_medicine, "pharmacy", 4.9f, "15-25 min"));
    }

    @Override
    public void getItemsByCategory(String category, DataCallback<List<DeliveryItem>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(500);
                List<DeliveryItem> filtered = allItems.stream()
                        .filter(item -> item.getCategory().equals(category))
                        .collect(Collectors.toList());
                mainHandler.post(() -> callback.onSuccess(filtered));
            } catch (InterruptedException e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    @Override
    public void getPopularItems(DataCallback<List<DeliveryItem>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(500);
                mainHandler.post(() -> callback.onSuccess(new ArrayList<>(allItems)));
            } catch (InterruptedException e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    @Override
    public void getRecommendedItems(DataCallback<List<DeliveryItem>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(700);
                List<DeliveryItem> recommended = allItems.stream().limit(4).collect(Collectors.toList());
                mainHandler.post(() -> callback.onSuccess(recommended));
            } catch (InterruptedException e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    @Override
    public void searchItems(String query, DataCallback<List<DeliveryItem>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(500);
                String queryLower = query.toLowerCase();
                List<DeliveryItem> filtered = allItems.stream()
                        .filter(item -> item.getName().toLowerCase().contains(queryLower) || 
                                       item.getDescription().toLowerCase().contains(queryLower))
                        .collect(Collectors.toList());
                mainHandler.post(() -> callback.onSuccess(filtered));
            } catch (InterruptedException e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }
}