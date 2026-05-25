package com.example.hellodelivery.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.hellodelivery.R;
import com.example.hellodelivery.databinding.ItemTrendingFoodBinding;
import com.example.hellodelivery.models.TrendingFood;
import java.util.List;
import java.util.Locale;

public class TrendingFoodAdapter extends RecyclerView.Adapter<TrendingFoodAdapter.ViewHolder> {

    private List<TrendingFood> items;
    private final OnTrendingFoodClickListener listener;

    public interface OnTrendingFoodClickListener {
        void onFoodClick(TrendingFood food);
        void onFavoriteClick(TrendingFood food);
        void onAddToCartClick(TrendingFood food);
    }

    public TrendingFoodAdapter(List<TrendingFood> items, OnTrendingFoodClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void updateList(List<TrendingFood> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrendingFoodBinding binding = ItemTrendingFoodBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrendingFood food = items.get(position);
        
        holder.binding.foodName.setText(food.getName());
        holder.binding.restaurantName.setText(food.getRestaurantName());
        holder.binding.tvRating.setText(String.valueOf(food.getRating()));
        holder.binding.tvDeliveryTime.setText(food.getDeliveryTime());
        holder.binding.tvPrice.setText(String.format(Locale.getDefault(), "$%.2f", food.getPrice()));
        
        if (food.getDiscountPrice() > 0) {
            holder.binding.tvOriginalPrice.setVisibility(View.VISIBLE);
            holder.binding.tvOriginalPrice.setText(String.format(Locale.getDefault(), "$%.2f", food.getDiscountPrice()));
            holder.binding.tvOriginalPrice.setPaintFlags(holder.binding.tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            
            int discount = (int) ((1 - (food.getPrice() / food.getDiscountPrice())) * 100);
            holder.binding.tvDiscountBadge.setVisibility(View.VISIBLE);
            holder.binding.tvDiscountBadge.setText(String.format(Locale.getDefault(), "%d%% OFF", discount));
        } else {
            holder.binding.tvOriginalPrice.setVisibility(View.GONE);
            holder.binding.tvDiscountBadge.setVisibility(View.GONE);
        }

        holder.binding.trendingBadge.setVisibility(food.isTrending() ? View.VISIBLE : View.GONE);
        holder.binding.btnFavorite.setImageResource(food.isFavorite() ? 
                android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

        Glide.with(holder.itemView.getContext())
                .load(food.getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.featured_placeholder)
                .into(holder.binding.foodImage);

        holder.itemView.setOnClickListener(v -> listener.onFoodClick(food));
        
        holder.binding.btnFavorite.setOnClickListener(v -> {
            food.setFavorite(!food.isFavorite());
            notifyItemChanged(position);
            v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in));
            listener.onFavoriteClick(food);
        });

        holder.binding.btnAddToCart.setOnClickListener(v -> {
            v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100).withEndAction(() -> 
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
            ).start();
            listener.onAddToCartClick(food);
        });

        // Entry animation
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTrendingFoodBinding binding;
        ViewHolder(ItemTrendingFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
