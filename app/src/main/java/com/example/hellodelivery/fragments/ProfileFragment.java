package com.example.hellodelivery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.hellodelivery.activities.EditProfileActivity;
import com.example.hellodelivery.activities.LoginActivity;
import com.example.hellodelivery.activities.SavedAddressesActivity;
import com.example.hellodelivery.activities.SettingsActivity;
import com.example.hellodelivery.databinding.FragmentProfileBinding;
import com.example.hellodelivery.models.User;
import com.example.hellodelivery.utils.SessionManager;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(requireContext());

        updateUI();
    }

    private void updateUI() {
        if (sessionManager.isLoggedIn()) {
            User user = sessionManager.getUserDetails();
            if (user != null) {
                binding.profileName.setText(user.getName());
                binding.profileEmail.setText(user.getEmail());
            }
            binding.btnLogout.setText("Logout");
            binding.btnLogout.setOnClickListener(v -> {
                sessionManager.logout();
                updateUI();
            });
            
            binding.btnEditProfile.setEnabled(true);
            binding.btnSavedAddresses.setEnabled(true);
            binding.btnSettings.setEnabled(true);
        } else {
            binding.profileName.setText("Guest User");
            binding.profileEmail.setText("Login to access all features");
            binding.btnLogout.setText("Login / Register");
            binding.btnLogout.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            });
            
            binding.btnEditProfile.setEnabled(false);
            binding.btnSavedAddresses.setEnabled(false);
            binding.btnSettings.setEnabled(false);
        }

        binding.btnEditProfile.setOnClickListener(v -> {
            if (checkLogin()) startActivity(new Intent(getActivity(), EditProfileActivity.class));
        });

        binding.btnSavedAddresses.setOnClickListener(v -> {
            if (checkLogin()) startActivity(new Intent(getActivity(), SavedAddressesActivity.class));
        });

        binding.btnSettings.setOnClickListener(v -> {
            if (checkLogin()) startActivity(new Intent(getActivity(), SettingsActivity.class));
        });
    }

    private boolean checkLogin() {
        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
