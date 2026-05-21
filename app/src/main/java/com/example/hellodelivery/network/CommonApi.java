package com.example.hellodelivery.network;

import com.example.hellodelivery.models.Banner;
import com.example.hellodelivery.models.Category;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CommonApi {
    @GET("banners")
    Call<List<Banner>> getBanners();

    @GET("categories")
    Call<List<Category>> getCategories();
}
