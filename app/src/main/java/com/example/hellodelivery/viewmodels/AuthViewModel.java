package com.example.hellodelivery.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.hellodelivery.models.User;
import com.example.hellodelivery.repositories.AuthRepository;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;

    public AuthViewModel() {
        authRepository = new AuthRepository();
    }

    public LiveData<User> login(String email, String password) {
        return authRepository.login(email, password);
    }

    public LiveData<User> register(String name, String email, String phone, String password) {
        return authRepository.register(name, email, phone, password);
    }
}
