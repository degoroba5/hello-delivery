package com.example.hellodelivery.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.hellodelivery.models.Product;
import com.example.hellodelivery.models.TrendingFood;
import com.example.hellodelivery.repositories.ProductRepository;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private final ProductRepository productRepository;

    public ProductViewModel() {
        productRepository = new ProductRepository();
    }

    public LiveData<List<Product>> getProductsByStore(String storeId) {
        return productRepository.getProductsByStore(storeId);
    }

    public LiveData<List<Product>> getPopularProducts() {
        return productRepository.getPopularProducts();
    }

    public LiveData<List<TrendingFood>> getTrendingFoods() {
        return productRepository.getTrendingFoods();
    }

    public LiveData<List<Product>> getSpecialOffers() {
        return productRepository.getSpecialOffers();
    }

    public LiveData<Product> getProductDetails(String productId) {
        return productRepository.getProductDetails(productId);
    }

    public LiveData<List<Product>> searchProducts(String query) {
        return productRepository.searchProducts(query);
    }
}
