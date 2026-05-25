package com.example.hellodelivery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.hellodelivery.activities.ProductDetailsActivity;
import com.example.hellodelivery.adapters.ProductAdapter;
import com.example.hellodelivery.database.CartEntity;
import com.example.hellodelivery.databinding.FragmentSearchBinding;
import com.example.hellodelivery.models.Product;
import com.example.hellodelivery.utils.SessionManager;
import com.example.hellodelivery.viewmodels.CartViewModel;
import com.example.hellodelivery.viewmodels.ProductViewModel;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private ProductViewModel productViewModel;
    private CartViewModel cartViewModel;
    private SessionManager sessionManager;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        sessionManager = new SessionManager(requireContext());

        setupRecyclerView();
        setupSearchListener();
        
        binding.emptySearchState.setVisibility(View.VISIBLE);
    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter(new ArrayList<>(), product -> {
            Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
            intent.putExtra("product_id", product.getId());
            startActivity(intent);
        });
        
        adapter.setOnAddToCartClickListener(product -> {
            if (checkLogin()) {
                addToCart(product);
            }
        });
        
        binding.searchResultsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchResultsRecycler.setAdapter(adapter);
    }

    private boolean checkLogin() {
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(getContext(), "Please login to add items to cart", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return false;
        }
        return true;
    }

    private void addToCart(Product product) {
        double price = product.getDiscountPrice() > 0 ? product.getDiscountPrice() : product.getPrice();
        CartEntity cartItem = new CartEntity(
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                price,
                1,
                product.getStoreId()
        );
        cartViewModel.addToCart(cartItem);
        Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
    }

    private void setupSearchListener() {
        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    performSearch(s.toString());
                } else {
                    adapter.updateList(new ArrayList<>());
                    binding.emptySearchState.setVisibility(View.VISIBLE);
                    binding.searchResultsRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void performSearch(String query) {
        productViewModel.searchProducts(query).observe(getViewLifecycleOwner(), products -> {
            if (products != null && !products.isEmpty()) {
                adapter.updateList(products);
                binding.emptySearchState.setVisibility(View.GONE);
                binding.searchResultsRecycler.setVisibility(View.VISIBLE);
            } else {
                adapter.updateList(new ArrayList<>());
                binding.emptySearchState.setVisibility(View.VISIBLE);
                binding.searchResultsRecycler.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
