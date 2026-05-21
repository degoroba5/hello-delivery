package com.example.hellodelivery.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.hellodelivery.models.Store;
import com.example.hellodelivery.network.RetrofitClient;
import com.example.hellodelivery.network.StoreApi;
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
