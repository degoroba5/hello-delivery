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
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.hellodelivery.activities.ProductDetailsActivity;
import com.example.hellodelivery.adapters.ProductAdapter;
import com.example.hellodelivery.database.AppDatabase;
import com.example.hellodelivery.databinding.FragmentFavoritesBinding;
import com.example.hellodelivery.models.Product;
import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        observeFavorites();

        binding.btnExploreProducts.setOnClickListener(v -> {
            // Navigate to Home or Search
        });
    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter(new ArrayList<>(), product -> {
            Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
            intent.putExtra("product_id", product.getId());
            startActivity(intent);
        });
        binding.favoritesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.favoritesRecyclerView.setAdapter(adapter);
    }

    private void observeFavorites() {
        AppDatabase.getInstance(requireContext()).favoriteDao().getAllFavorites().observe(getViewLifecycleOwner(), favorites -> {
            if (favorites != null && !favorites.isEmpty()) {
                List<Product> products = new ArrayList<>();
                for (com.example.hellodelivery.database.FavoriteEntity fav : favorites) {
                    products.add(new Product(fav.getProductId(), fav.getProductName(), "", fav.getPrice(), 0, fav.getImageUrl(), "", "", 0, 0));
                }
                adapter.updateList(products);
                binding.emptyFavoritesState.setVisibility(View.GONE);
                binding.favoritesRecyclerView.setVisibility(View.VISIBLE);
            } else {
                binding.emptyFavoritesState.setVisibility(View.VISIBLE);
                binding.favoritesRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
