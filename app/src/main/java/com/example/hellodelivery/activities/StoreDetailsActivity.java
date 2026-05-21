package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.example.hellodelivery.adapters.ProductAdapter;
import com.example.hellodelivery.databinding.ActivityStoreDetailsBinding;
import com.example.hellodelivery.models.Store;
import com.example.hellodelivery.viewmodels.ProductViewModel;
import com.example.hellodelivery.viewmodels.StoreViewModel;
import java.util.ArrayList;

public class StoreDetailsActivity extends AppCompatActivity {

    private ActivityStoreDetailsBinding binding;
    private StoreViewModel storeViewModel;
    private ProductViewModel productViewModel;
    private ProductAdapter productAdapter;
    private String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storeId = getIntent().getStringExtra("store_id");
        if (storeId == null) {
            finish();
            return;
        }

        storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        setupToolbar();
        setupRecyclerView();
        loadStoreDetails();
        loadProducts();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        productAdapter = new ProductAdapter(new ArrayList<>(), product -> {
            Intent intent = new Intent(StoreDetailsActivity.this, ProductDetailsActivity.class);
            intent.putExtra("product_id", product.getId());
            startActivity(intent);
        });
        binding.productRecyclerViewDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.productRecyclerViewDetail.setAdapter(productAdapter);
    }

    private void loadStoreDetails() {
        storeViewModel.getStoreDetails(storeId).observe(this, store -> {
            if (store != null) {
                displayStoreDetails(store);
            } else {
                Toast.makeText(this, "Failed to load store details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayStoreDetails(Store store) {
        binding.storeNameDetail.setText(store.getName());
        binding.storeDescriptionDetail.setText(store.getDescription());
        binding.storeRatingDetail.setText(store.getRating() + " (500+ ratings)");
        binding.deliveryTimeDetail.setText(store.getDeliveryTime());
        Glide.with(this).load(store.getImageUrl()).into(binding.storeBannerImage);
    }

    private void loadProducts() {
        productViewModel.getProductsByStore(storeId).observe(this, products -> {
            if (products != null) {
                productAdapter.updateList(products);
            }
        });
    }
}
