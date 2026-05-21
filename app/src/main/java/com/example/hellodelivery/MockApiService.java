package com.example.hellodelivery;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockApiService implements ApiService {
    private final List<DeliveryItem> allItems = new ArrayList<>();
    private final List<Restaurant> allRestaurants = new ArrayList<>();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public MockApiService() {
        // Food Items - International
        allItems.add(new DeliveryItem("1", "Classic Burger", "Juicy beef patty with cheese, tomato, and lettuce.", 550.00, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=800&auto=format&fit=crop", "food", 4.5f, "20-30 min", "Burger King"));
        allItems.add(new DeliveryItem("2", "Pepperoni Pizza", "Large pizza with spicy pepperoni and mozzarella.", 850.00, "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?q=80&w=800&auto=format&fit=crop", "food", 4.8f, "30-45 min", "Pizza Hut"));
        allItems.add(new DeliveryItem("5", "Sushi Set", "12 piece assorted premium sushi.", 1800.00, "file:///android_asset/items/sushiset.png", "food", 4.6f, "40-50 min", "Sakura Japanese"));
        
        // Food Items - Ethiopian Traditional
        allItems.add(new DeliveryItem("10", "Doro Wat", "Traditional spicy chicken stew served with boiled eggs and fresh Injera.", 1200.00, "file:///android_asset/items/Dorowet.png", "food", 4.9f, "35-45 min", "Habesha Restaurant"));
        allItems.add(new DeliveryItem("11", "Shiro Wat", "Delicious chickpea powder stew served with warm Injera.", 450.00, "file:///android_asset/items/ShroWet.png", "food", 4.7f, "20-30 min", "Yod Abyssinia"));
        allItems.add(new DeliveryItem("12", "Kitfo", "Minced beef marinated in spicy mitmita and herbal butter.", 1400.00, "file:///android_asset/items/Kitfo.png", "food", 4.8f, "25-35 min", "Toteot Kitfo"));
        allItems.add(new DeliveryItem("13", "Beyaynetu", "A vibrant platter of various traditional vegetarian stews.", 650.00, "file:///android_asset/items/Beyaynetu.png", "food", 4.9f, "25-30 min", "Cultural Hub"));
        allItems.add(new DeliveryItem("14", "Tibs", "Sautéed tender meat chunks with onions and green peppers.", 1100.00, "https://lowcarbapi.com/wp-content/uploads/2022/07/Awaze-Tibe-Egyptian-Beef-Tibs-Recipe-IG-1.jpg", "food", 4.7f, "20-35 min", "Sheger Grill"));

        // Best Reviewed Food (5.0 Stars)
        allItems.add(new DeliveryItem("20", "Special Meat Platter", "Chef's selection of premium meats with unique Ethiopian spices.", 2800.00, "file:///android_asset/items/Specialmeatplatter.png", "food", 5.0f, "40-50 min", "Grand Habesha"));
        allItems.add(new DeliveryItem("21", "Premium Coffee Ceremony", "Traditional roasted coffee with popcorn and aromatic incense.", 300.00, "file:///android_asset/items/Coffee.png", "food", 5.0f, "20-30 min", "Tomoca Coffee"));
        allItems.add(new DeliveryItem("22", "Super Veggie Delight", "All-in-one vegetarian feast with extra local sides.", 950.00, "file:///android_asset/items/VeggieDelight.png", "food", 5.0f, "25-35 min", "Pure Green"));
        allItems.add(new DeliveryItem("25", "Mahberawi Platter", "The ultimate mix of meat and veggie stews on fresh Injera.", 3500.00, "file:///android_asset/items/mahberawiplatter.png", "food", 5.0f, "45-60 min", "2000 Habesha"));

        // Mock Restaurants
        allRestaurants.add(new Restaurant("Habesha Restaurant", "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400", 4.9f, "35-45 min"));
        allRestaurants.add(new Restaurant("Yod Abyssinia", "https://images.unsplash.com/photo-1552566626-52f8b828add9?w=400", 4.7f, "20-30 min"));
        allRestaurants.add(new Restaurant("Burger King", "https://images.unsplash.com/photo-1610348725531-843dff563e2c?w=400", 4.5f, "20-30 min"));
        allRestaurants.add(new Restaurant("Pizza Hut", "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400", 4.8f, "30-45 min"));
        allRestaurants.add(new Restaurant("Tomoca Coffee", "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400", 5.0f, "10-20 min"));
    }

    @Override
    public void getItemsByCategory(String category, DataCallback<List<DeliveryItem>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(300);
                List<DeliveryItem> filtered = allItems.stream()
                        .filter(item -> item.getCategory().equals(category))
                        .collect(Collectors.toList());
                mainHandler.post(() -> callback.onSuccess(filtered));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    @Override
    public void getPopularItems(DataCallback<List<DeliveryItem>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(300);
                mainHandler.post(() -> callback.onSuccess(new ArrayList<>(allItems)));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    @Override
    public void getRecommendedItems(DataCallback<List<DeliveryItem>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(300);
                List<DeliveryItem> recommended = allItems.stream()
                        .filter(item -> item.getRating() >= 4.8f)
                        .collect(Collectors.toList());
                mainHandler.post(() -> callback.onSuccess(recommended));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    @Override
    public void getBestReviewedItems(DataCallback<List<DeliveryItem>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(300);
                List<DeliveryItem> bestReviewed = allItems.stream()
                        .filter(item -> item.getRating() >= 4.9f && item.getCategory().equals("food"))
                        .sorted((i1, i2) -> Float.compare(i2.getRating(), i1.getRating()))
                        .collect(Collectors.toList());
                mainHandler.post(() -> callback.onSuccess(bestReviewed));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    @Override
    public void searchItems(String query, DataCallback<List<DeliveryItem>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(300);
                String q = query.toLowerCase();
                List<DeliveryItem> filtered = allItems.stream()
                        .filter(item -> item.getName().toLowerCase().contains(q))
                        .collect(Collectors.toList());
                mainHandler.post(() -> callback.onSuccess(filtered));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    @Override
    public void getOrderHistory(DataCallback<List<Order>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(500);
                List<Order> history = new ArrayList<>();
                List<DeliveryItem> order1 = new ArrayList<>();
                order1.add(allItems.get(0));
                order1.add(allItems.get(4));
                history.add(new Order("ORD-1024", order1, 1000.00, "Oct 28, 2023"));

                List<DeliveryItem> order2 = new ArrayList<>();
                order2.add(allItems.get(3));
                history.add(new Order("ORD-1025", order2, 1200.00, "Nov 02, 2023"));

                mainHandler.post(() -> callback.onSuccess(history));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    @Override
    public void getRestaurants(DataCallback<List<Restaurant>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(300);
                mainHandler.post(() -> callback.onSuccess(new ArrayList<>(allRestaurants)));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }

    @Override
    public void getItemsByRestaurant(String restaurantName, DataCallback<List<DeliveryItem>> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(300);
                List<DeliveryItem> filtered = allItems.stream()
                        .filter(item -> item.getRestaurantName().equals(restaurantName))
                        .collect(Collectors.toList());
                mainHandler.post(() -> callback.onSuccess(filtered));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }
}
