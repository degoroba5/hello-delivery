package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.hellodelivery.adapters.CartAdapterEnterprise;
import com.example.hellodelivery.database.AppDatabase;
import com.example.hellodelivery.database.CartEntity;
import com.example.hellodelivery.databinding.ActivityCartBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private CartAdapterEnterprise adapter;
    private List<CartEntity> cartItems = new ArrayList<>();
    private double subtotal = 0;
    private final double deliveryFee = 5.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupToolbar();
        setupRecyclerView();
        observeCart();

        binding.btnCheckout.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
            } else {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        adapter = new CartAdapterEnterprise(cartItems, new CartAdapterEnterprise.OnCartItemChangeListener() {
            @Override
            public void onQuantityChanged(CartEntity item, int newQuantity) {
                if (newQuantity <= 0) {
                    removeFromCart(item);
                } else {
                    item.setQuantity(newQuantity);
                    updateCartInDb(item);
                }
            }

            @Override
            public void onRemoveItem(CartEntity item) {
                removeFromCart(item);
            }
        });
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecyclerView.setAdapter(adapter);
    }

    private void observeCart() {
        AppDatabase.getInstance(this).cartDao().getAllCartItems().observe(this, items -> {
            if (items != null) {
                this.cartItems = items;
                adapter.updateList(items);
                calculateTotal();
            }
        });
    }

    private void calculateTotal() {
        subtotal = 0;
        for (CartEntity item : cartItems) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        binding.cartSubtotal.setText(String.format(Locale.getDefault(), "$%.2f", subtotal));
        binding.cartDeliveryFee.setText(String.format(Locale.getDefault(), "$%.2f", deliveryFee));
        binding.cartTotal.setText(String.format(Locale.getDefault(), "$%.2f", subtotal + deliveryFee));
    }

    private void updateCartInDb(CartEntity item) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).cartDao().updateCart(item);
        });
    }

    private void removeFromCart(CartEntity item) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).cartDao().removeFromCart(item);
        });
    }
}
