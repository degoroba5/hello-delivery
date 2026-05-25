package com.example.hellodelivery.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.hellodelivery.models.Store;
import com.example.hellodelivery.network.RetrofitClient;
import com.example.hellodelivery.network.StoreApi;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreRepository {
    private final StoreApi storeApi;

    public StoreRepository() {
        storeApi = RetrofitClient.getClient().create(StoreApi.class);
    }

    public LiveData<List<Store>> getAllStores() {
        MutableLiveData<List<Store>> data = new MutableLiveData<>();
        storeApi.getAllStores().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(getMockStores());
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                data.setValue(getMockStores());
            }
        });
        return data;
    }

    private List<Store> getMockStores() {
        List<Store> stores = new ArrayList<>();
        stores.add(new Store("s1", "Burger King", "Best burgers in town", "https://images.unsplash.com/photo-1610348725531-843dff563e2c?w=400", 4.5f, "20-30 min", 2.0, "Silicon Valley", true, "Burger"));
        stores.add(new Store("s2", "Pizza Hut", "Fresh pizza every day", "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400", 4.8f, "30-45 min", 1.5, "Silicon Valley", true, "Pizza"));
        stores.add(new Store("s3", "KFC", "Crispy fried chicken", "https://images.unsplash.com/photo-1562967914-608f82629710?w=400", 4.4f, "20-30 min", 2.5, "Silicon Valley", true, "Chicken"));
        stores.add(new Store("s4", "Habesha Restaurant", "Traditional Ethiopian Food", "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400", 4.9f, "35-45 min", 3.0, "Silicon Valley", true, "Traditional"));
        stores.add(new Store("s5", "Sweet Delights", "Premium desserts and cakes", "https://images.unsplash.com/photo-1551024506-0bccd828d307?w=400", 4.7f, "25-35 min", 1.0, "Silicon Valley", true, "Desserts"));
        stores.add(new Store("s6", "Fresh Juice Bar", "Natural fruit juices", "https://images.unsplash.com/photo-1544145945-f904253d0c7b?w=400", 4.6f, "15-20 min", 1.5, "Silicon Valley", true, "Drinks"));
        stores.add(new Store("s7", "Starbucks", "World's best coffee", "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400", 4.8f, "10-20 min", 2.0, "Silicon Valley", true, "Coffee"));
        stores.add(new Store("s8", "Pasta House", "Authentic Italian pasta", "https://images.unsplash.com/photo-1473093226795-af9932fe5856?w=400", 4.7f, "20-30 min", 2.5, "Silicon Valley", true, "Pasta"));
        stores.add(new Store("s9", "Subway", "Eat fresh sandwiches", "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=400", 4.5f, "15-20 min", 1.0, "Silicon Valley", true, "Sandwich"));
        stores.add(new Store("s10", "Healthy Bites", "Organic and healthy meals", "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", 4.8f, "20-30 min", 2.0, "Silicon Valley", true, "Healthy"));
        stores.add(new Store("s11", "Taco Bell", "Mexican fast food", "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=400", 4.4f, "15-25 min", 1.5, "Silicon Valley", true, "Fast Food"));
        stores.add(new Store("s12", "Seafood Grill", "Fresh from the ocean", "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?w=400", 4.9f, "30-40 min", 3.0, "Silicon Valley", true, "Seafood"));
        return stores;
    }

    public LiveData<List<Store>> getNearbyStores(double lat, double lng) {
        MutableLiveData<List<Store>> data = new MutableLiveData<>();
        storeApi.getNearbyStores(lat, lng).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Store> getStoreDetails(String storeId) {
        MutableLiveData<Store> data = new MutableLiveData<>();
        storeApi.getStoreDetails(storeId).enqueue(new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Store> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
