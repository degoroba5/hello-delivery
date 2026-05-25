package com.example.hellodelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.hellodelivery.MainActivity;
import com.example.hellodelivery.adapters.OnboardingAdapter;
import com.example.hellodelivery.databinding.ActivityOnboardingBinding;
import com.example.hellodelivery.utils.SessionManager;
import com.google.android.material.tabs.TabLayoutMediator;

public class OnboardingActivity extends AppCompatActivity {

    private ActivityOnboardingBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        OnboardingAdapter adapter = new OnboardingAdapter(this);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.indicator, binding.viewPager, (tab, position) -> {
            // No text needed for dots
        }).attach();

        binding.btnNext.setOnClickListener(v -> {
            if (binding.viewPager.getCurrentItem() < adapter.getItemCount() - 1) {
                binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
            } else {
                completeOnboarding();
            }
        });

        binding.btnSkip.setOnClickListener(v -> completeOnboarding());

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == adapter.getItemCount() - 1) {
                    binding.btnNext.setText("Get Started");
                } else {
                    binding.btnNext.setText("Next");
                }
            }
        });
    }

    private void completeOnboarding() {
        sessionManager.setFirstTime(false);
        // Enterprise UX: After onboarding, go straight to Home (MainActivity) as a Guest
        Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
