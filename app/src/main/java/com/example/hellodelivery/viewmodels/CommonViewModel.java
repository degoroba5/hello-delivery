package com.example.hellodelivery.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.hellodelivery.models.Banner;
import com.example.hellodelivery.models.Category;
import com.example.hellodelivery.network.CommonApi;
import com.example.hellodelivery.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonViewModel extends ViewModel {
    private final CommonApi commonApi;

    public CommonViewModel() {
        commonApi = RetrofitClient.getClient().create(CommonApi.class);
    }

    public LiveData<List<Banner>> getBanners() {
        MutableLiveData<List<Banner>> data = new MutableLiveData<>();
        commonApi.getBanners().enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<Category>> getCategories() {
        MutableLiveData<List<Category>> data = new MutableLiveData<>();
        commonApi.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
