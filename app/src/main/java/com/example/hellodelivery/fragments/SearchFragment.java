package com.example.hellodelivery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.hellodelivery.activities.ProductDetailsActivity;
import com.example.hellodelivery.adapters.ProductAdapter;
import com.example.hellodelivery.databinding.FragmentSearchBinding;
import com.example.hellodelivery.viewmodels.ProductViewModel;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private ProductViewModel productViewModel;
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
        binding.searchResultsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchResultsRecycler.setAdapter(adapter);
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
