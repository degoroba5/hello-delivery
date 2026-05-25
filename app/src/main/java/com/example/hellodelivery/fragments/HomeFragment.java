package com.example.hellodelivery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.hellodelivery.R;
import com.example.hellodelivery.activities.LoginActivity;
import com.example.hellodelivery.activities.StoreDetailsActivity;
import com.example.hellodelivery.activities.ProductDetailsActivity;
import com.example.hellodelivery.activities.CartActivity;
import com.example.hellodelivery.adapters.BannerAdapter;
import com.example.hellodelivery.adapters.CategoryAdapter;
import com.example.hellodelivery.adapters.StoreAdapter;
import com.example.hellodelivery.adapters.ProductAdapter;
import com.example.hellodelivery.adapters.TrendingFoodAdapter;
import com.example.hellodelivery.database.CartEntity;
import com.example.hellodelivery.databinding.FragmentHomeBinding;
import com.example.hellodelivery.models.Category;
import com.example.hellodelivery.models.Product;
import com.example.hellodelivery.models.Store;
import com.example.hellodelivery.models.TrendingFood;
import com.example.hellodelivery.utils.Constants;
import com.example.hellodelivery.utils.SessionManager;
import com.example.hellodelivery.viewmodels.CartViewModel;
import com.example.hellodelivery.viewmodels.CommonViewModel;
import com.example.hellodelivery.viewmodels.StoreViewModel;
import com.example.hellodelivery.viewmodels.ProductViewModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enterprise Mobile Product Design - Home Screen
 * Implementation of a high-conversion, premium food discovery experience.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private StoreViewModel storeViewModel;
    private ProductViewModel productViewModel;
    private CommonViewModel commonViewModel;
    private CartViewModel cartViewModel;
    private SessionManager sessionManager;
    
    private StoreAdapter nearbyStoreAdapter;
    private StoreAdapter popularStoreAdapter;
    private TrendingFoodAdapter trendingFoodAdapter;
    private ProductAdapter specialOffersAdapter;
    private ProductAdapter recentlyViewedAdapter;
    private BannerAdapter bannerAdapter;
    private CategoryAdapter categoryAdapter;

    private List<Store> allStores = new ArrayList<>();
    private List<TrendingFood> allTrendingFoods = new ArrayList<>();
    private List<Product> allPopularProducts = new ArrayList<>();
    private List<Product> allSpecialOffers = new ArrayList<>();
    private String currentCategoryName = "All";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        commonViewModel = new ViewModelProvider(this).get(CommonViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        sessionManager = new SessionManager(requireContext());

        updateDynamicGreeting();
        setupRecyclerViews();
        setupFloatingNavigation();
        
        // PAUSED: setupScrollInteractions();
        loadData();

        binding.swipeRefresh.setOnRefreshListener(this::loadData);
        
        binding.searchContainer.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_search);
        });
    }

    private void updateDynamicGreeting() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour < 12) greeting = "Good Morning ☀️";
        else if (hour < 17) greeting = "Good Afternoon 🌤️";
        else if (hour < 21) greeting = "Good Evening 👋";
        else greeting = "Good Night 🌙";
        binding.tvGreeting.setText(greeting);
    }

    private void setupRecyclerViews() {
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        LayoutAnimationController animation = new LayoutAnimationController(fadeIn);
        animation.setDelay(0.1f);
        animation.setOrder(LayoutAnimationController.ORDER_NORMAL);

        // 1. Banners (Enterprise Slider)
        bannerAdapter = new BannerAdapter(new ArrayList<>());
        binding.bannerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.bannerRecyclerView.setAdapter(bannerAdapter);

        // 2. Horizontal Categories - Now with real images and full filtering
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), this::applyCategoryFilter);
        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.categoryRecyclerView.setAdapter(categoryAdapter);

        // 3. Special Offers Section
        specialOffersAdapter = createProductAdapter();
        binding.specialOffersRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.specialOffersRecycler.setAdapter(specialOffersAdapter);

        // 4. Nearby Restaurants (Vertical List)
        nearbyStoreAdapter = createStoreAdapter();
        binding.storeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.storeRecyclerView.setAdapter(nearbyStoreAdapter);
        binding.storeRecyclerView.setLayoutAnimation(animation);

        // 5. Popular Restaurants (Horizontal)
        popularStoreAdapter = createStoreAdapter();
        binding.popularStoresRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.popularStoresRecycler.setAdapter(popularStoreAdapter);

        // 6. Trending Foods (Horizontal) - Premium Upgrade
        trendingFoodAdapter = new TrendingFoodAdapter(new ArrayList<>(), new TrendingFoodAdapter.OnTrendingFoodClickListener() {
            @Override
            public void onFoodClick(TrendingFood food) {
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra("product_id", food.getId());
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(TrendingFood food) {
                String message = food.isFavorite() ? "Added to favorites! ❤️" : "Removed from favorites";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddToCartClick(TrendingFood food) {
                handleTrendingAddToCart(food);
            }
        });
        binding.trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.trendingRecyclerView.setAdapter(trendingFoodAdapter);

        // 7. Recently Viewed (Simulation)
        recentlyViewedAdapter = createProductAdapter();
        binding.recentlyViewedRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
    }

    private void applyCategoryFilter(Category category) {
        currentCategoryName = category.getName();
        String categoryId = category.getId();
        
        // 1. Filter Stores (Nearby Restaurants)
        if (currentCategoryName.equalsIgnoreCase("All")) {
            nearbyStoreAdapter.updateList(allStores);
            binding.tvNearbyStoresTitle.setText("Nearby Restaurants");
        } else {
            List<Store> filteredStores = allStores.stream()
                    .filter(s -> s.getCategory() != null && s.getCategory().equalsIgnoreCase(currentCategoryName))
                    .collect(Collectors.toList());
            nearbyStoreAdapter.updateList(filteredStores);
            binding.tvNearbyStoresTitle.setText(currentCategoryName + " Restaurants Near You");
        }
        binding.storeRecyclerView.scheduleLayoutAnimation();

        // 2. Filter Trending Foods
        if (currentCategoryName.equalsIgnoreCase("All")) {
            trendingFoodAdapter.updateList(allTrendingFoods);
            binding.tvTrendingTitle.setText("Trending Now 🔥");
        } else {
            List<TrendingFood> filteredTrending = allTrendingFoods.stream()
                    .filter(f -> f.getCategoryName() != null && f.getCategoryName().equalsIgnoreCase(currentCategoryName))
                    .collect(Collectors.toList());
            trendingFoodAdapter.updateList(filteredTrending);
            binding.tvTrendingTitle.setText("Trending " + currentCategoryName + " 🔥");
        }
        binding.trendingRecyclerView.scheduleLayoutAnimation();

        // 3. Filter Special Offers
        if (currentCategoryName.equalsIgnoreCase("All")) {
            specialOffersAdapter.updateList(allSpecialOffers);
            binding.tvSpecialOffersTitle.setText("Special Offers for You");
        } else {
            List<Product> filteredOffers = allSpecialOffers.stream()
                    .filter(p -> p.getCategoryId() != null && p.getCategoryId().equals(categoryId))
                    .collect(Collectors.toList());
            specialOffersAdapter.updateList(filteredOffers);
            binding.tvSpecialOffersTitle.setText(currentCategoryName + " Deals");
        }
        binding.specialOffersRecycler.scheduleLayoutAnimation();

        // 4. Filter Popular Products (used for Recently Viewed simulation)
        if (currentCategoryName.equalsIgnoreCase("All")) {
            recentlyViewedAdapter.updateList(allPopularProducts);
            binding.tvRecentlyViewedTitle.setText("Continue Browsing");
        } else {
            List<Product> filteredProducts = allPopularProducts.stream()
                    .filter(p -> p.getCategoryId() != null && p.getCategoryId().equals(categoryId))
                    .collect(Collectors.toList());
            recentlyViewedAdapter.updateList(filteredProducts);
            binding.tvRecentlyViewedTitle.setText("Top " + currentCategoryName + " Picks");
        }
        binding.recentlyViewedRecycler.scheduleLayoutAnimation();
    }

    private ProductAdapter createProductAdapter() {
        ProductAdapter adapter = new ProductAdapter(new ArrayList<>(), product -> {
            Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
            intent.putExtra("product_id", product.getId());
            startActivity(intent);
        });
        adapter.setOnAddToCartClickListener(this::handleAddToCart);
        return adapter;
    }

    private StoreAdapter createStoreAdapter() {
        return new StoreAdapter(new ArrayList<>(), store -> {
            Intent intent = new Intent(getActivity(), StoreDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_STORE_ID, store.getId());
            startActivity(intent);
        });
    }

    private void setupFloatingNavigation() {
        binding.floatingBottomNavigation.setOnItemSelectedListener(item -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            int id = item.getItemId();
            if (id == R.id.nav_home) return true;
            if (id == R.id.nav_search) { navController.navigate(R.id.nav_search); return true; }
            if (id == R.id.nav_cart) {
                startActivity(new Intent(getActivity(), CartActivity.class));
                return false;
            }
            if (id == R.id.nav_orders) { navController.navigate(R.id.nav_orders); return true; }
            if (id == R.id.nav_profile) { navController.navigate(R.id.nav_profile); return true; }
            return false;
        });
    }

    private void setupScrollInteractions() {
        binding.nestedScroll.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > oldScrollY + 25) {
                binding.floatingNav.animate().translationY(binding.floatingNav.getHeight() + 200).alpha(0f).setDuration(400).start();
            } else if (scrollY < oldScrollY - 25) {
                binding.floatingNav.animate().translationY(0).alpha(1f).setDuration(400).start();
            }
        });
    }

    private void handleAddToCart(Product product) {
        // High conversion standard: allow guest checkout additions
        double price = product.getDiscountPrice() > 0 ? product.getDiscountPrice() : product.getPrice();
        CartEntity cartItem = new CartEntity(product.getId(), product.getName(), product.getImageUrl(), price, 1, product.getStoreId());
        cartViewModel.addToCart(cartItem);
        Toast.makeText(getContext(), product.getName() + " added to your cart! 🛒", Toast.LENGTH_SHORT).show();
    }

    private void handleTrendingAddToCart(TrendingFood food) {
        // High conversion standard: allow guest checkout additions
        CartEntity cartItem = new CartEntity(food.getId(), food.getName(), food.getImageUrl(), food.getPrice(), 1, food.getStoreId());
        cartViewModel.addToCart(cartItem);
        Toast.makeText(getContext(), food.getName() + " added to your cart! 🛒", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        binding.swipeRefresh.setRefreshing(true);
        
        // Show trending shimmer
        binding.trendingShimmer.startShimmer();
        binding.trendingShimmer.setVisibility(View.VISIBLE);
        binding.trendingRecyclerView.setVisibility(View.GONE);

        commonViewModel.getBanners().observe(getViewLifecycleOwner(), banners -> {
            if (banners != null) bannerAdapter.updateList(banners);
        });

        commonViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) categoryAdapter.updateList(categories);
        });
        
        storeViewModel.getAllStores().observe(getViewLifecycleOwner(), stores -> {
            binding.swipeRefresh.setRefreshing(false);
            if (stores != null) {
                this.allStores = stores;
                nearbyStoreAdapter.updateList(stores);
                
                // Popular Restaurants (Rating >= 4.7)
                List<Store> popular = stores.stream().filter(s -> s.getRating() >= 4.7).limit(10).collect(Collectors.toList());
                popularStoreAdapter.updateList(popular);
            }
        });

        productViewModel.getTrendingFoods().observe(getViewLifecycleOwner(), trendingFoods -> {
            binding.trendingShimmer.stopShimmer();
            binding.trendingShimmer.setVisibility(View.GONE);
            binding.trendingRecyclerView.setVisibility(View.VISIBLE);
            
            if (trendingFoods != null) {
                this.allTrendingFoods = trendingFoods;
                trendingFoodAdapter.updateList(trendingFoods);
            }
        });

        productViewModel.getPopularProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                this.allPopularProducts = products;
                recentlyViewedAdapter.updateList(new ArrayList<>(products));
            }
        });

        productViewModel.getSpecialOffers().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                this.allSpecialOffers = products;
                specialOffersAdapter.updateList(products);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
