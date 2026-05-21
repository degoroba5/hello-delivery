package com.example.hellodelivery.network;

import com.example.hellodelivery.models.User;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/login")
    Call<User> login(@Body HashMap<String, String> credentials);

    @POST("auth/register")
    Call<User> register(@Body HashMap<String, String> userData);

    @POST("auth/verify-otp")
    Call<User> verifyOtp(@Body HashMap<String, String> otpData);
}
