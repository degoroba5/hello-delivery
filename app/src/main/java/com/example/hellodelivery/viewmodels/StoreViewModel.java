package com.example.hellodelivery.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.hellodelivery.models.Store;
import com.example.hellodelivery.repositories.StoreRepository;
import java.util.List;

public class StoreViewModel extends ViewModel {
    private final StoreRepository storeRepository;

    public StoreViewModel() {
        storeRepository = new StoreRepository();
    }

    public LiveData<List<Store>> getAllStores() {
        return storeRepository.getAllStores();
    }

    public LiveData<List<Store>> getNearbyStores(double lat, double lng) {
        return storeRepository.getNearbyStores(lat, lng);
    }

    public LiveData<Store> getStoreDetails(String storeId) {
        return storeRepository.getStoreDetails(storeId);
    }
}
