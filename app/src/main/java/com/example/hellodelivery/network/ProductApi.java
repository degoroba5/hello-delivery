package com.example.hellodelivery.network;

import com.example.hellodelivery.models.Product;
import com.example.hellodelivery.models.TrendingFood;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductApi {
    @GET("products")
    Call<List<Product>> getProductsByStore(@Query("storeId") String storeId);

    @GET("products/popular")
    Call<List<Product>> getPopularProducts();

    @GET("products/offers")
    Call<List<Product>> getSpecialOffers();

    @GET("products/{id}")
    Call<Product> getProductDetails(@Path("id") String productId);

    @GET("products/search")
    Call<List<Product>> searchProducts(@Query("q") String query);

    @GET("trending-foods")
    Call<List<TrendingFood>> getTrendingFoods();
}
