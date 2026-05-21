package com.example.hellodelivery.network;

import com.example.hellodelivery.models.Order;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApi {
    @POST("orders")
    Call<Order> placeOrder(@Header("Authorization") String token, @Body HashMap<String, Object> orderData);

    @GET("orders/user")
    Call<List<Order>> getOrderHistory(@Header("Authorization") String token);

    @GET("orders/{id}")
    Call<Order> getOrderDetails(@Header("Authorization") String token, @Path("id") String orderId);
}
