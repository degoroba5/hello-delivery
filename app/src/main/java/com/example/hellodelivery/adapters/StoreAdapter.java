package com.example.hellodelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hellodelivery.databinding.ItemStoreBinding;
import com.example.hellodelivery.models.Store;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<Store> storeList;
    private final OnStoreClickListener listener;

    public interface OnStoreClickListener {
        void onStoreClick(Store store);
    }

    public StoreAdapter(List<Store> storeList, OnStoreClickListener listener) {
        this.storeList = storeList;
        this.listener = listener;
    }

    public void updateList(List<Store> newList) {
        this.storeList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStoreBinding binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StoreViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = storeList.get(position);
        holder.binding.storeName.setText(store.getName());
        holder.binding.storeDescription.setText(store.getDescription());
        holder.binding.storeRating.setText(String.valueOf(store.getRating()));
        holder.binding.deliveryTime.setText(store.getDeliveryTime());
        
        Glide.with(holder.itemView.getContext())
                .load(store.getImageUrl())
                .into(holder.binding.storeImage);

        holder.itemView.setOnClickListener(v -> listener.onStoreClick(store));
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {
        ItemStoreBinding binding;
        StoreViewHolder(ItemStoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
