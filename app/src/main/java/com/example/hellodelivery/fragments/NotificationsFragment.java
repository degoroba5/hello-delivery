package com.example.hellodelivery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.hellodelivery.adapters.NotificationAdapter;
import com.example.hellodelivery.databinding.FragmentNotificationsBinding;
import com.example.hellodelivery.models.Notification;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        loadNotifications();
    }

    private void setupRecyclerView() {
        adapter = new NotificationAdapter(new ArrayList<>(), notification -> {
            // Handle notification click
            notification.setRead(true);
            adapter.notifyDataSetChanged();
        });
        binding.notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.notificationsRecyclerView.setAdapter(adapter);
    }

    private void loadNotifications() {
        // Mocking notifications
        List<Notification> list = new ArrayList<>();
        list.add(new Notification("1", "Order Confirmed", "Your order #12345 has been confirmed.", "2 min ago", "ORDER", false));
        list.add(new Notification("2", "Big Discount!", "Get 50% off on your next burger order.", "1 hour ago", "PROMO", true));
        list.add(new Notification("3", "System Update", "We've improved our map tracking feature.", "Yesterday", "SYSTEM", true));
        
        adapter.updateList(list);
        
        if (list.isEmpty()) {
            binding.emptyNotificationsState.setVisibility(View.VISIBLE);
        } else {
            binding.emptyNotificationsState.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
