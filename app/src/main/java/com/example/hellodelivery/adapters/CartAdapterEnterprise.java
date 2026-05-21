package com.example.hellodelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hellodelivery.database.CartEntity;
import com.example.hellodelivery.databinding.ItemCartEnterpriseBinding;
import java.util.List;
import java.util.Locale;

public class CartAdapterEnterprise extends RecyclerView.Adapter<CartAdapterEnterprise.CartViewHolder> {

    private List<CartEntity> cartItems;
    private final OnCartItemChangeListener listener;

    public interface OnCartItemChangeListener {
        void onQuantityChanged(CartEntity item, int newQuantity);
        void onRemoveItem(CartEntity item);
    }

    public CartAdapterEnterprise(List<CartEntity> cartItems, OnCartItemChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    public void updateList(List<CartEntity> newList) {
        this.cartItems = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartEnterpriseBinding binding = ItemCartEnterpriseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartEntity item = cartItems.get(position);
        holder.binding.productName.setText(item.getProductName());
        holder.binding.productPrice.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice()));
        holder.binding.quantityText.setText(String.valueOf(item.getQuantity()));

        Glide.with(holder.itemView.getContext())
                .load(item.getProductImageUrl())
                .into(holder.binding.productImage);

        holder.binding.btnIncrease.setOnClickListener(v -> listener.onQuantityChanged(item, item.getQuantity() + 1));
        holder.binding.btnDecrease.setOnClickListener(v -> listener.onQuantityChanged(item, item.getQuantity() - 1));
        holder.binding.btnRemove.setOnClickListener(v -> listener.onRemoveItem(item));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ItemCartEnterpriseBinding binding;
        CartViewHolder(ItemCartEnterpriseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
