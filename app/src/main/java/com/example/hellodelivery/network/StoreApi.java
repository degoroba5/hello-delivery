package com.example.hellodelivery.network;

import com.example.hellodelivery.models.Store;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StoreApi {
    @GET("stores")
    Call<List<Store>> getAllStores();

    @GET("stores/nearby")
    Call<List<Store>> getNearbyStores(@Query("lat") double lat, @Query("lng") double lng);

    @GET("stores/{id}")
    Call<Store> getStoreDetails(@Path("id") String storeId);
}
