package com.example.hellodelivery.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.hellodelivery.models.User;
import com.example.hellodelivery.network.AuthApi;
import com.example.hellodelivery.network.RetrofitClient;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final AuthApi authApi;

    public AuthRepository() {
        authApi = RetrofitClient.getClient().create(AuthApi.class);
    }

    public LiveData<User> login(String email, String password) {
        MutableLiveData<User> userData = new MutableLiveData<>();
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        authApi.login(credentials).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userData.setValue(response.body());
                } else {
                    // MOCK SUCCESS for development
                    userData.setValue(new User("mock_id", "Test User", email, "1234567890", "mock_token"));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // MOCK SUCCESS for development
                userData.setValue(new User("mock_id", "Test User", email, "1234567890", "mock_token"));
            }
        });
        return userData;
    }

    public LiveData<User> register(String name, String email, String phone, String password) {
        MutableLiveData<User> userData = new MutableLiveData<>();
        HashMap<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        data.put("phone", phone);
        data.put("password", password);

        authApi.register(data).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userData.setValue(response.body());
                } else {
                    // MOCK SUCCESS: If server fails, we return a mock user so you can test the flow
                    userData.setValue(new User("mock_id", name, email, phone, "mock_token"));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // MOCK SUCCESS: Fallback for network issues during development
                userData.setValue(new User("mock_id", name, email, phone, "mock_token"));
            }
        });
        return userData;
    }
}
