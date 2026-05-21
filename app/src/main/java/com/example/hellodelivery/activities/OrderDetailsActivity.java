package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.hellodelivery.databinding.ActivityOrderDetailsBinding;
import com.example.hellodelivery.models.Order;
import com.example.hellodelivery.utils.SessionManager;
import com.example.hellodelivery.viewmodels.OrderViewModel;
import java.util.Locale;

public class OrderDetailsActivity extends AppCompatActivity {

    private ActivityOrderDetailsBinding binding;
    private OrderViewModel orderViewModel;
    private SessionManager sessionManager;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        orderId = getIntent().getStringExtra("order_id");
        if (orderId == null) {
            finish();
            return;
        }

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        sessionManager = new SessionManager(this);

        setupToolbar();
        loadOrderDetails();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void loadOrderDetails() {
        String token = sessionManager.getToken();
        // Assuming we add getOrderDetails to ViewModel/Repo
        // For now, let's assume we fetch it
    }

    private void displayOrderDetails(Order order) {
        binding.tvOrderId.setText(String.format("Order #%s", order.getId().toUpperCase()));
        binding.tvOrderStatus.setText(order.getStatus());
        binding.tvOrderDate.setText(order.getCreatedAt());
        binding.tvDeliveryAddressDetail.setText(order.getAddress());
        binding.tvTotalPaid.setText(String.format(Locale.getDefault(), "$%.2f", order.getTotalAmount()));

        binding.orderItemsList.removeAllViews();
        for (Order.OrderItem item : order.getItems()) {
            TextView tv = new TextView(this);
            tv.setText(String.format(Locale.getDefault(), "%dx %s - $%.2f", item.getQuantity(), item.getName(), item.getPrice() * item.getQuantity()));
            tv.setPadding(0, 8, 0, 8);
            binding.orderItemsList.addView(tv);
        }

        if (order.getStatus().equalsIgnoreCase("On the way") || order.getStatus().equalsIgnoreCase("Preparing")) {
            binding.btnTrackOrderDetail.setVisibility(View.VISIBLE);
            binding.btnTrackOrderDetail.setOnClickListener(v -> {
                Intent intent = new Intent(OrderDetailsActivity.this, OrderTrackingActivity.class);
                intent.putExtra("order_id", order.getId());
                startActivity(intent);
            });
        } else {
            binding.btnTrackOrderDetail.setVisibility(View.GONE);
        }
    }
}
