package com.example.hellodelivery.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.hellodelivery.models.Order;
import com.example.hellodelivery.repositories.OrderRepository;
import java.util.HashMap;
import java.util.List;

public class OrderViewModel extends ViewModel {
    private final OrderRepository orderRepository;

    public OrderViewModel() {
        orderRepository = new OrderRepository();
    }

    public LiveData<Order> placeOrder(String token, HashMap<String, Object> orderData) {
        return orderRepository.placeOrder(token, orderData);
    }

    public LiveData<List<Order>> getOrderHistory(String token) {
        return orderRepository.getOrderHistory(token);
    }

    public LiveData<Order> getOrderDetails(String token, String orderId) {
        return orderRepository.getOrderDetails(token, orderId);
    }
}
