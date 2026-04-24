package com.example.hellodelivery;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button btnFood, btnGrocery, btnPharmacy, btnPackage;
    private CardView featuredCard;
    private Toolbar toolbar;
    private RecyclerView popularItemsRecyclerView;
    private RecyclerView recommendedItemsRecyclerView;
    
    private ApiService apiService;
    private DeliveryAdapter popularAdapter;
    private DeliveryAdapter recommendedAdapter;

    // Track selected category
    private String currentCategory = "food";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize Backend Service
        apiService = new MockApiService();

        // Setup toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Blue Delivery");
        }

        // Handle Window Insets for Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views
        searchEditText = findViewById(R.id.search_edit_text);
        btnFood = findViewById(R.id.btn_food);
        btnGrocery = findViewById(R.id.btn_grocery);
        btnPharmacy = findViewById(R.id.btn_pharmacy);
        btnPackage = findViewById(R.id.btn_package);
        featuredCard = findViewById(R.id.featured_card);
        popularItemsRecyclerView = findViewById(R.id.popular_items_recycler_view);
        recommendedItemsRecyclerView = findViewById(R.id.recommended_items_recycler_view);

        setupRecyclerViews();
        setupListeners();
        updateCategorySelection(currentCategory);
        
        // Initial data load
        loadInitialData();
    }

    private void setupRecyclerViews() {
        if (popularItemsRecyclerView != null) {
            popularAdapter = new DeliveryAdapter(new ArrayList<>());
            popularItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            popularItemsRecyclerView.setAdapter(popularAdapter);
            // Disable nested scrolling as it's inside a NestedScrollView
            popularItemsRecyclerView.setNestedScrollingEnabled(false);
        }
        
        if (recommendedItemsRecyclerView != null) {
            recommendedAdapter = new DeliveryAdapter(new ArrayList<>());
            recommendedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            recommendedItemsRecyclerView.setAdapter(recommendedAdapter);
            recommendedItemsRecyclerView.setNestedScrollingEnabled(false);
        }
    }

    private void setupListeners() {
        // Search with better validation
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                            event.getAction() == KeyEvent.ACTION_DOWN)) {
                performSearch();
                return true;
            }
            return false;
        });

        // Category Listeners with visual feedback
        btnFood.setOnClickListener(v -> {
            currentCategory = "food";
            updateCategorySelection(currentCategory);
            loadCategoryContent(currentCategory);
        });

        btnGrocery.setOnClickListener(v -> {
            currentCategory = "grocery";
            updateCategorySelection(currentCategory);
            loadCategoryContent(currentCategory);
        });

        btnPharmacy.setOnClickListener(v -> {
            currentCategory = "pharmacy";
            updateCategorySelection(currentCategory);
            loadCategoryContent(currentCategory);
        });

        btnPackage.setOnClickListener(v -> {
            currentCategory = "package";
            updateCategorySelection(currentCategory);
            loadCategoryContent(currentCategory);
        });

        // Featured Card Listener
        featuredCard.setOnClickListener(v -> {
            showToast("Opening special offers for " + currentCategory);
        });
    }

    private void loadInitialData() {
        apiService.getPopularItems(new ApiService.DataCallback<List<DeliveryItem>>() {
            @Override
            public void onSuccess(List<DeliveryItem> data) {
                popularAdapter.updateItems(data);
            }

            @Override
            public void onError(String error) {
                showToast("Error loading popular: " + error);
            }
        });

        apiService.getRecommendedItems(new ApiService.DataCallback<List<DeliveryItem>>() {
            @Override
            public void onSuccess(List<DeliveryItem> data) {
                recommendedAdapter.updateItems(data);
            }

            @Override
            public void onError(String error) {
                showToast("Error loading recommended: " + error);
            }
        });
    }

    private void performSearch() {
        String query = searchEditText.getText().toString().trim();
        if (query.isEmpty()) {
            showToast("Please enter a search term");
            return;
        }

        apiService.searchItems(query, new ApiService.DataCallback<List<DeliveryItem>>() {
            @Override
            public void onSuccess(List<DeliveryItem> data) {
                popularAdapter.updateItems(data);
                if (data.isEmpty()) {
                    showToast("No results found for: " + query);
                }
            }

            @Override
            public void onError(String error) {
                showToast("Search error: " + error);
            }
        });
    }

    private void updateCategorySelection(String category) {
        resetButtonStyles();

        switch (category) {
            case "food":
                btnFood.setBackgroundColor(getColor(R.color.blue_primary));
                btnFood.setTextColor(getColor(R.color.white));
                break;
            case "grocery":
                btnGrocery.setBackgroundColor(getColor(R.color.blue_primary));
                btnGrocery.setTextColor(getColor(R.color.white));
                break;
            case "pharmacy":
                btnPharmacy.setBackgroundColor(getColor(R.color.blue_primary));
                btnPharmacy.setTextColor(getColor(R.color.white));
                break;
            case "package":
                btnPackage.setBackgroundColor(getColor(R.color.blue_primary));
                btnPackage.setTextColor(getColor(R.color.white));
                break;
        }
    }

    private void resetButtonStyles() {
        int defaultTextColor = getColor(R.color.blue_primary);
        btnFood.setBackgroundColor(getColor(android.R.color.transparent));
        btnFood.setTextColor(defaultTextColor);
        btnGrocery.setBackgroundColor(getColor(android.R.color.transparent));
        btnGrocery.setTextColor(defaultTextColor);
        btnPharmacy.setBackgroundColor(getColor(android.R.color.transparent));
        btnPharmacy.setTextColor(defaultTextColor);
        btnPackage.setBackgroundColor(getColor(android.R.color.transparent));
        btnPackage.setTextColor(defaultTextColor);
    }

    private void loadCategoryContent(String category) {
        apiService.getItemsByCategory(category, new ApiService.DataCallback<List<DeliveryItem>>() {
            @Override
            public void onSuccess(List<DeliveryItem> data) {
                popularAdapter.updateItems(data);
                // Also update recommended for the category if needed
                recommendedAdapter.updateItems(data);
            }

            @Override
            public void onError(String error) {
                showToast("Error loading category: " + error);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}