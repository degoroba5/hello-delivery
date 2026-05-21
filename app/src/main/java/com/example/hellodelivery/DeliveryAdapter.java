package com.example.hellodelivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Locale;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(DeliveryItem item);
    }

    private List<DeliveryItem> items;
    private OnItemClickListener listener;
    private boolean isHorizontal;

    public DeliveryAdapter(List<DeliveryItem> items, OnItemClickListener listener) {
        this(items, listener, false);
    }

    public DeliveryAdapter(List<DeliveryItem> items, OnItemClickListener listener, boolean isHorizontal) {
        this.items = items;
        this.listener = listener;
        this.isHorizontal = isHorizontal;
    }

    public void updateItems(List<DeliveryItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = isHorizontal ? R.layout.item_delivery_horizontal : R.layout.item_delivery;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeliveryItem item = items.get(position);
        holder.nameTextView.setText(item.getName());
        holder.descriptionTextView.setText(item.getDescription());
        holder.priceTextView.setText(String.format(Locale.getDefault(), "%.2f ETB", item.getPrice()));
        holder.ratingTextView.setText(String.format(Locale.getDefault(), "%.1f", item.getRating()));
        
        if (holder.restaurantTextView != null) {
            holder.restaurantTextView.setText(item.getRestaurantName());
        }
        
        if (holder.timeTextView != null) {
            holder.timeTextView.setText(item.getDeliveryTime());
        }
        
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(holder.imageView.getContext())
                .load(item.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.featured_placeholder)
                .into(holder.imageView);
        } else if (item.getImageResId() != 0) {
            holder.imageView.setImageResource(item.getImageResId());
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        TextView ratingTextView;
        TextView timeTextView;
        TextView restaurantTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            nameTextView = itemView.findViewById(R.id.item_name);
            descriptionTextView = itemView.findViewById(R.id.item_description);
            priceTextView = itemView.findViewById(R.id.item_price);
            ratingTextView = itemView.findViewById(R.id.item_rating);
            timeTextView = itemView.findViewById(R.id.item_time);
            restaurantTextView = itemView.findViewById(R.id.item_restaurant);
        }
    }
}