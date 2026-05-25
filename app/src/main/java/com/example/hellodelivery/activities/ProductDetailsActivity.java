package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.hellodelivery.R;
import com.example.hellodelivery.database.CartEntity;
import com.example.hellodelivery.database.FavoriteEntity;
import com.example.hellodelivery.databinding.ActivityProductDetailsBinding;
import com.example.hellodelivery.models.Product;
import com.example.hellodelivery.utils.SessionManager;
import com.example.hellodelivery.viewmodels.CartViewModel;
import com.example.hellodelivery.viewmodels.FavoriteViewModel;
import com.example.hellodelivery.viewmodels.ProductViewModel;
import java.util.Locale;

public class ProductDetailsActivity extends AppCompatActivity {

    private ActivityProductDetailsBinding binding;
    private ProductViewModel productViewModel;
    private CartViewModel cartViewModel;
    private FavoriteViewModel favoriteViewModel;
    private SessionManager sessionManager;
    
    private String productId;
    private int quantity = 1;
    private Product currentProduct;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productId = getIntent().getStringExtra("product_id");
        if (productId == null) {
            finish();
            return;
        }

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        sessionManager = new SessionManager(this);

        setupToolbar();
        setupListeners();
        loadProductDetails();
        checkIfFavorite();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupListeners() {
        binding.btnIncrease.setOnClickListener(v -> {
            quantity++;
            updateQuantityText();
        });

        binding.btnDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantityText();
            }
        });

        // Guest users CAN add to cart
        binding.btnAddToCartLarge.setOnClickListener(v -> addToCart());
        
        // Favorites still require login
        binding.btnFavorite.setOnClickListener(v -> {
            if (checkLogin("save favorites")) {
                toggleFavorite();
            }
        });
    }

    private boolean checkLogin(String action) {
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "Please login to " + action, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            return false;
        }
        return true;
    }

    private void updateQuantityText() {
        binding.tvQuantity.setText(String.valueOf(quantity));
    }

    private void loadProductDetails() {
        productViewModel.getProductDetails(productId).observe(this, product -> {
            if (product != null) {
                currentProduct = product;
                displayProductDetails(product);
            } else {
                Toast.makeText(this, "Failed to load product details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProductDetails(Product product) {
        binding.productNameDetail.setText(product.getName());
        binding.productDescriptionDetail.setText(product.getDescription());
        
        double price = product.getDiscountPrice() > 0 ? product.getDiscountPrice() : product.getPrice();
        binding.productPriceDetail.setText(String.format(Locale.getDefault(), "$%.2f", price));
        
        if (product.getDiscountPrice() > 0) {
            binding.productOriginalPriceDetail.setVisibility(View.VISIBLE);
            binding.productOriginalPriceDetail.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
        } else {
            binding.productOriginalPriceDetail.setVisibility(View.GONE);
        }

        Glide.with(this)
                .load(product.getImageUrl())
                .placeholder(R.drawable.featured_placeholder)
                .into(binding.productImageLarge);
    }

    private void checkIfFavorite() {
        if (!sessionManager.isLoggedIn()) return;

        favoriteViewModel.isFavorite(productId, isFav -> {
            isFavorite = isFav;
            runOnUiThread(() -> updateFavoriteUI());
        });
    }

    private void toggleFavorite() {
        if (currentProduct == null) return;
        
        if (isFavorite) {
            favoriteViewModel.removeFavorite(new FavoriteEntity(currentProduct.getId(), currentProduct.getName(), currentProduct.getImageUrl(), currentProduct.getPrice()));
            isFavorite = false;
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            favoriteViewModel.addFavorite(new FavoriteEntity(currentProduct.getId(), currentProduct.getName(), currentProduct.getImageUrl(), currentProduct.getPrice()));
            isFavorite = true;
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        }
        updateFavoriteUI();
    }

    private void updateFavoriteUI() {
        binding.btnFavorite.setImageResource(isFavorite ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
    }

    private void addToCart() {
        if (currentProduct == null) return;

        double priceToUse = currentProduct.getDiscountPrice() > 0 ? currentProduct.getDiscountPrice() : currentProduct.getPrice();

        CartEntity cartItem = new CartEntity(
                currentProduct.getId(),
                currentProduct.getName(),
                currentProduct.getImageUrl(),
                priceToUse,
                quantity,
                currentProduct.getStoreId()
        );

        cartViewModel.addToCart(cartItem);
        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        finish();
    }
}
