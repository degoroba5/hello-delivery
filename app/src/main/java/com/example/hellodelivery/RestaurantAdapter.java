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

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    private List<Restaurant> restaurants;
    private OnRestaurantClickListener listener;

    public RestaurantAdapter(List<Restaurant> restaurants, OnRestaurantClickListener listener) {
        this.restaurants = restaurants;
        this.listener = listener;
    }

    public void updateRestaurants(List<Restaurant> newRestaurants) {
        this.restaurants = newRestaurants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.nameTextView.setText(restaurant.getName());
        holder.ratingTextView.setText(String.format(Locale.getDefault(), "%.1f", restaurant.getRating()));
        
        if (restaurant.getImageUrl() != null && !restaurant.getImageUrl().isEmpty()) {
            Glide.with(holder.imageView.getContext())
                .load(restaurant.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.featured_placeholder)
                .into(holder.imageView);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRestaurantClick(restaurant);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView ratingTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurant_image);
            nameTextView = itemView.findViewById(R.id.restaurant_name);
            ratingTextView = itemView.findViewById(R.id.restaurant_rating);
        }
    }
}