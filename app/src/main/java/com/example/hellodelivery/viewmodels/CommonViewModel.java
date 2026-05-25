package com.example.hellodelivery.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.hellodelivery.models.Banner;
import com.example.hellodelivery.models.Category;
import java.util.ArrayList;
import java.util.List;

public class CommonViewModel extends ViewModel {
    private final MutableLiveData<List<Banner>> banners = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();

    public CommonViewModel() {
        loadMockData();
    }

    public LiveData<List<Banner>> getBanners() {
        return banners;
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    private void loadMockData() {
        List<Banner> bannerList = new ArrayList<>();
        bannerList.add(new Banner("1", "Premium Burgers 40% OFF", "https://img.freepik.com/free-psd/food-delivery-banner-template_23-2148913937.jpg"));
        bannerList.add(new Banner("2", "Healthy Salads Special", "https://img.freepik.com/free-vector/flat-food-delivery-landing-page-template_23-2149028637.jpg"));
        banners.setValue(bannerList);

        List<Category> categoryList = new ArrayList<>();
        // Using high-quality real food images from Unsplash for a premium look
        categoryList.add(new Category("0", "All", "https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("1", "Burger", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("2", "Pizza", "https://images.unsplash.com/photo-1513104890138-7c749659a591?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("3", "Chicken", "https://images.unsplash.com/photo-1567620905732-2d1ec7bb7445?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("4", "Traditional", "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("5", "Desserts", "https://images.unsplash.com/photo-1551024506-0bccd828d307?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("6", "Drinks", "https://images.unsplash.com/photo-1544145945-f904253d0c7b?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("7", "Coffee", "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("8", "Pasta", "https://images.unsplash.com/photo-1473093226795-af9932fe5856?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("9", "Sandwich", "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("10", "Healthy", "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("11", "Fast Food", "https://images.unsplash.com/photo-1561758033-d89a9ad46330?q=80&w=200&auto=format&fit=crop"));
        categoryList.add(new Category("12", "Seafood", "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?q=80&w=200&auto=format&fit=crop"));

        categories.setValue(categoryList);
    }
}
