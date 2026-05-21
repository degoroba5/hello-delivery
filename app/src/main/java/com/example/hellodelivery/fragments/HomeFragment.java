package com.example.hellodelivery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.hellodelivery.R;
import com.example.hellodelivery.activities.StoreDetailsActivity;
import com.example.hellodelivery.activities.ProductDetailsActivity;
import com.example.hellodelivery.adapters.BannerAdapter;
import com.example.hellodelivery.adapters.CategoryAdapter;
import com.example.hellodelivery.adapters.StoreAdapter;
import com.example.hellodelivery.adapters.ProductAdapter;
import com.example.hellodelivery.databinding.FragmentHomeBinding;
import com.example.hellodelivery.utils.Constants;
import com.example.hellodelivery.viewmodels.CommonViewModel;
import com.example.hellodelivery.viewmodels.StoreViewModel;
import com.example.hellodelivery.viewmodels.ProductViewModel;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private StoreViewModel storeViewModel;
    private ProductViewModel productViewModel;
    private CommonViewModel commonViewModel;
    
    private StoreAdapter storeAdapter;
    private ProductAdapter productAdapter;
    private BannerAdapter bannerAdapter;
    private CategoryAdapter categoryAdapter;

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

        setupRecyclerViews();
        loadData();

        binding.swipeRefresh.setOnRefreshListener(this::loadData);
        
        binding.searchBar.setFocusable(false);
        binding.searchBar.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_search);
        });
    }

    private void setupRecyclerViews() {
        // Banners
        bannerAdapter = new BannerAdapter(new ArrayList<>());
        binding.bannerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.bannerRecyclerView.setAdapter(bannerAdapter);

        // Categories
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), category -> {
            // Filter logic
        });
        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.categoryRecyclerView.setAdapter(categoryAdapter);

        // Stores
        storeAdapter = new StoreAdapter(new ArrayList<>(), store -> {
            Intent intent = new Intent(getActivity(), StoreDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_STORE_ID, store.getId());
            startActivity(intent);
        });
        binding.storeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.storeRecyclerView.setAdapter(storeAdapter);

        // Popular Products
        productAdapter = new ProductAdapter(new ArrayList<>(), product -> {
            Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.getId());
            startActivity(intent);
        });
        binding.popularProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.popularProductsRecyclerView.setAdapter(productAdapter);
    }

    private void loadData() {
        binding.swipeRefresh.setRefreshing(true);
        
        commonViewModel.getBanners().observe(getViewLifecycleOwner(), banners -> {
            if (banners != null) {
                bannerAdapter.updateList(banners);
            }
        });

        commonViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                categoryAdapter.updateList(categories);
            }
        });
        
        storeViewModel.getAllStores().observe(getViewLifecycleOwner(), stores -> {
            binding.swipeRefresh.setRefreshing(false);
            if (stores != null) {
                storeAdapter.updateList(stores);
            }
        });

        productViewModel.getPopularProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                productAdapter.updateList(products);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
