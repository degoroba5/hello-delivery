package com.example.hellodelivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {

    private List<DeliveryItem> items;

    public DeliveryAdapter(List<DeliveryItem> items) {
        this.items = items;
    }

    public void updateItems(List<DeliveryItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeliveryItem item = items.get(position);
        holder.nameTextView.setText(item.getName());
        holder.descriptionTextView.setText(item.getDescription());
        holder.priceTextView.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice()));
        holder.ratingTextView.setText(String.format(Locale.getDefault(), "⭐ %.1f", item.getRating()));
        holder.timeTextView.setText(item.getDeliveryTime());
        
        // Set the image from local resources
        if (item.getImageResId() != 0) {
            holder.imageView.setImageResource(item.getImageResId());
        }
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            nameTextView = itemView.findViewById(R.id.item_name);
            descriptionTextView = itemView.findViewById(R.id.item_description);
            priceTextView = itemView.findViewById(R.id.item_price);
            ratingTextView = itemView.findViewById(R.id.item_rating);
            timeTextView = itemView.findViewById(R.id.item_time);
        }
    }
}