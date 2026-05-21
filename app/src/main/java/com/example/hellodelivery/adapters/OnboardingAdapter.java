package com.example.hellodelivery.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.hellodelivery.fragments.OnboardingPageFragment;

public class OnboardingAdapter extends FragmentStateAdapter {

    public OnboardingAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return OnboardingPageFragment.newInstance("Browse Stores", "Find the best restaurants and stores near you with just a tap.", android.R.drawable.ic_menu_search);
            case 1:
                return OnboardingPageFragment.newInstance("Easy Ordering", "Add items to your cart and customize your order easily.", android.R.drawable.ic_input_add);
            case 2:
                return OnboardingPageFragment.newInstance("Fast Delivery", "Real-time tracking of your delivery from store to your doorstep.", android.R.drawable.ic_menu_directions);
            default:
                return OnboardingPageFragment.newInstance("", "", 0);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
