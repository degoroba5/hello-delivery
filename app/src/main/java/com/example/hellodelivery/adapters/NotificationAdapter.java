package com.example.hellodelivery.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hellodelivery.databinding.ItemNotificationBinding;
import com.example.hellodelivery.models.Notification;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;
    private final OnNotificationClickListener listener;

    public interface OnNotificationClickListener {
        void onNotificationClick(Notification notification);
    }

    public NotificationAdapter(List<Notification> notificationList, OnNotificationClickListener listener) {
        this.notificationList = notificationList;
        this.listener = listener;
    }

    public void updateList(List<Notification> newList) {
        this.notificationList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.binding.tvNotificationTitle.setText(notification.getTitle());
        holder.binding.tvNotificationMessage.setText(notification.getMessage());
        holder.binding.tvNotificationTime.setText(notification.getTime());

        if (notification.isRead()) {
            holder.binding.getRoot().setCardBackgroundColor(Color.WHITE);
            holder.binding.unreadIndicator.setVisibility(View.GONE);
        } else {
            holder.binding.getRoot().setCardBackgroundColor(Color.parseColor("#F0F4FF"));
            holder.binding.unreadIndicator.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> listener.onNotificationClick(notification));
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;
        NotificationViewHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
