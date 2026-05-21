package com.example.hellodelivery.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.hellodelivery.models.Product;
import com.example.hellodelivery.network.ProductApi;
import com.example.hellodelivery.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private final ProductApi productApi;

    public ProductRepository() {
        productApi = RetrofitClient.getClient().create(ProductApi.class);
    }

    public LiveData<List<Product>> getProductsByStore(String storeId) {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        productApi.getProductsByStore(storeId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<Product>> getPopularProducts() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        productApi.getPopularProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Product> getProductDetails(String productId) {
        MutableLiveData<Product> data = new MutableLiveData<>();
        productApi.getProductDetails(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<Product>> searchProducts(String query) {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        productApi.searchProducts(query).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
