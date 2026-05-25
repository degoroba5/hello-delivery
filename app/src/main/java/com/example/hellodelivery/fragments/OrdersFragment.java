package com.example.hellodelivery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.hellodelivery.activities.LoginActivity;
import com.example.hellodelivery.adapters.OrderAdapterEnterprise;
import com.example.hellodelivery.databinding.FragmentOrdersBinding;
import com.example.hellodelivery.utils.SessionManager;
import com.example.hellodelivery.viewmodels.OrderViewModel;
import java.util.ArrayList;

public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;
    private OrderViewModel orderViewModel;
    private OrderAdapterEnterprise adapter;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        sessionManager = new SessionManager(requireContext());

        setupRecyclerView();
        
        if (sessionManager.isLoggedIn()) {
            binding.loginRequiredState.setVisibility(View.GONE);
            loadOrders();
        } else {
            showLoginRequired();
        }
    }

    private void showLoginRequired() {
        binding.ordersRecyclerView.setVisibility(View.GONE);
        binding.emptyOrdersState.setVisibility(View.GONE);
        binding.loginRequiredState.setVisibility(View.VISIBLE);
        
        binding.btnLoginOrders.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
    }

    private void setupRecyclerView() {
        adapter = new OrderAdapterEnterprise(new ArrayList<>(), new OrderAdapterEnterprise.OnOrderClickListener() {
            @Override
            public void onOrderClick(com.example.hellodelivery.models.Order order) {
                // Navigate to Order Details
            }

            @Override
            public void onReorderClick(com.example.hellodelivery.models.Order order) {
                Toast.makeText(getContext(), "Reordering items...", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.ordersRecyclerView.setAdapter(adapter);
    }

    private void loadOrders() {
        String token = sessionManager.getToken();
        if (token == null) return;

        orderViewModel.getOrderHistory(token).observe(getViewLifecycleOwner(), orders -> {
            if (orders != null && !orders.isEmpty()) {
                adapter.updateList(orders);
                binding.emptyOrdersState.setVisibility(View.GONE);
                binding.ordersRecyclerView.setVisibility(View.VISIBLE);
            } else {
                binding.emptyOrdersState.setVisibility(View.VISIBLE);
                binding.ordersRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
