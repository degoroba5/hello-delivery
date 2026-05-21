package com.example.hellodelivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public interface OnCartItemRemovedListener {
        void onRemove(int position);
    }

    private List<DeliveryItem> items;
    private OnCartItemRemovedListener listener;

    public CartAdapter(List<DeliveryItem> items, OnCartItemRemovedListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeliveryItem item = items.get(position);
        holder.nameTextView.setText(item.getName());
        holder.priceTextView.setText(String.format(Locale.getDefault(), "%.2f ETB", item.getPrice()));
        
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(holder.imageView.getContext())
                .load(item.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.featured_placeholder)
                .into(holder.imageView);
        } else if (item.getImageResId() != 0) {
            holder.imageView.setImageResource(item.getImageResId());
        }

        holder.removeButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemove(position);
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
        TextView priceTextView;
        ImageButton removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cart_item_image);
            nameTextView = itemView.findViewById(R.id.cart_item_name);
            priceTextView = itemView.findViewById(R.id.cart_item_price);
            removeButton = itemView.findViewById(R.id.btn_remove_cart);
        }
    }
}