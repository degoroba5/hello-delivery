package com.example.hellodelivery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.hellodelivery.databinding.FragmentOnboardingPageBinding;

public class OnboardingPageFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_IMAGE = "image";

    public static OnboardingPageFragment newInstance(String title, String description, int imageResId) {
        OnboardingPageFragment fragment = new OnboardingPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, description);
        args.putInt(ARG_IMAGE, imageResId);
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentOnboardingPageBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOnboardingPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            binding.tvTitle.setText(getArguments().getString(ARG_TITLE));
            binding.tvDescription.setText(getArguments().getString(ARG_DESCRIPTION));
            binding.ivOnboarding.setImageResource(getArguments().getInt(ARG_IMAGE));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
