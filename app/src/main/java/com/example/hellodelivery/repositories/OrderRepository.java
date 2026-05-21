package com.example.hellodelivery.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.hellodelivery.models.Order;
import com.example.hellodelivery.network.OrderApi;
import com.example.hellodelivery.network.RetrofitClient;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    private final OrderApi orderApi;

    public OrderRepository() {
        orderApi = RetrofitClient.getClient().create(OrderApi.class);
    }

    public LiveData<Order> placeOrder(String token, HashMap<String, Object> orderData) {
        MutableLiveData<Order> data = new MutableLiveData<>();
        orderApi.placeOrder("Bearer " + token, orderData).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<Order>> getOrderHistory(String token) {
        MutableLiveData<List<Order>> data = new MutableLiveData<>();
        orderApi.getOrderHistory("Bearer " + token).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Order> getOrderDetails(String token, String orderId) {
        MutableLiveData<Order> data = new MutableLiveData<>();
        orderApi.getOrderDetails("Bearer " + token, orderId).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
