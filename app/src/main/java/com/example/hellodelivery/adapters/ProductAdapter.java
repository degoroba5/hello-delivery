package com.example.hellodelivery.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hellodelivery.R;
import com.example.hellodelivery.databinding.ItemProductBinding;
import com.example.hellodelivery.models.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private final OnProductClickListener listener;
    private OnAddToCartClickListener addToCartListener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public interface OnAddToCartClickListener {
        void onAddToCart(Product product);
    }

    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    public void setOnAddToCartClickListener(OnAddToCartClickListener listener) {
        this.addToCartListener = listener;
    }

    public void updateList(List<Product> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.binding.productName.setText(product.getName());
        holder.binding.productPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.binding.productRating.setText(String.format("%.1f", product.getRating()));
        
        // Premium animation on entry
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.fade_in));

        if (product.getDiscountPrice() > 0) {
            holder.binding.productDiscountPrice.setVisibility(View.VISIBLE);
            holder.binding.productDiscountPrice.setText(String.format("$%.2f", product.getDiscountPrice()));
            holder.binding.productPrice.setPaintFlags(holder.binding.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.binding.productPrice.setTextColor(holder.itemView.getContext().getColor(R.color.text_tertiary));
        } else {
            holder.binding.productDiscountPrice.setVisibility(View.GONE);
            holder.binding.productPrice.setPaintFlags(holder.binding.productPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.binding.productPrice.setTextColor(holder.itemView.getContext().getColor(R.color.primary));
        }

        Glide.with(holder.itemView.getContext())
                .load(product.getImageUrl())
                .placeholder(R.drawable.featured_placeholder)
                .centerCrop()
                .into(holder.binding.productImage);

        holder.itemView.setOnClickListener(v -> listener.onProductClick(product));
        
        holder.binding.addToCartBtn.setOnClickListener(v -> {
            // Micro-interaction: button pop effect
            v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100).withEndAction(() -> 
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
            ).start();

            if (addToCartListener != null) {
                addToCartListener.onAddToCart(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;
        ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
