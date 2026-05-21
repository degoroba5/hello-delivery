package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.hellodelivery.database.AppDatabase;
import com.example.hellodelivery.database.CartEntity;
import com.example.hellodelivery.databinding.ActivityCheckoutBinding;
import com.example.hellodelivery.utils.Constants;
import com.example.hellodelivery.utils.SessionManager;
import com.example.hellodelivery.viewmodels.OrderViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class CheckoutActivity extends AppCompatActivity {

    private ActivityCheckoutBinding binding;
    private OrderViewModel orderViewModel;
    private SessionManager sessionManager;
    private List<CartEntity> cartItems = new ArrayList<>();
    private double subtotal = 0;
    private final double deliveryFee = 5.00;
    private double tax = 0;
    private double total = 0;

    private final ActivityResultLauncher<Intent> addressPickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String address = result.getData().getStringExtra("address");
                    binding.tvDeliveryAddress.setText(address);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        sessionManager = new SessionManager(this);

        setupToolbar();
        calculateSummary();

        binding.addressCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddressPickerActivity.class);
            addressPickerLauncher.launch(intent);
        });

        binding.btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void calculateSummary() {
        AppDatabase.getInstance(this).cartDao().getAllCartItems().observe(this, items -> {
            if (items != null) {
                this.cartItems = items;
                subtotal = 0;
                for (CartEntity item : items) {
                    subtotal += item.getPrice() * item.getQuantity();
                }
                tax = subtotal * 0.05;
                total = subtotal + deliveryFee + tax;

                binding.summarySubtotal.setText(String.format(Locale.getDefault(), "$%.2f", subtotal));
                binding.summaryDeliveryFee.setText(String.format(Locale.getDefault(), "$%.2f", deliveryFee));
                binding.summaryTax.setText(String.format(Locale.getDefault(), "$%.2f", tax));
                binding.summaryTotal.setText(String.format(Locale.getDefault(), "$%.2f", total));
            }
        });
    }

    private void placeOrder() {
        String address = binding.tvDeliveryAddress.getText().toString();
        if (address.equals("Select delivery address") || address.isEmpty()) {
            Toast.makeText(this, "Please select a delivery address", Toast.LENGTH_SHORT).show();
            return;
        }

        String paymentMethod = binding.radioCod.isChecked() ? "COD" : "Online";
        String token = sessionManager.getToken();

        HashMap<String, Object> orderData = new HashMap<>();
        orderData.put("address", address);
        orderData.put("paymentMethod", paymentMethod);
        orderData.put("totalAmount", total);
        
        List<HashMap<String, Object>> items = new ArrayList<>();
        for (CartEntity cartItem : cartItems) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("productId", cartItem.getProductId());
            item.put("quantity", cartItem.getQuantity());
            item.put("price", cartItem.getPrice());
            items.add(item);
        }
        orderData.put("items", items);

        binding.btnPlaceOrder.setEnabled(false);
        orderViewModel.placeOrder(token, orderData).observe(this, order -> {
            binding.btnPlaceOrder.setEnabled(true);
            if (order != null) {
                clearCartAndFinish(order.getId());
            } else {
                Toast.makeText(this, "Failed to place order. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearCartAndFinish(String orderId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).cartDao().clearCart();
            runOnUiThread(() -> {
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CheckoutActivity.this, OrderTrackingActivity.class);
                intent.putExtra(Constants.EXTRA_ORDER_ID, orderId);
                startActivity(intent);
                finish();
            });
        });
    }
}
