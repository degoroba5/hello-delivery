package com.example.hellodelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.hellodelivery.R;
import com.example.hellodelivery.databinding.ItemCategoryBinding;
import com.example.hellodelivery.models.Category;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private final OnCategoryClickListener listener;
    private int selectedPosition = 0;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoryAdapter(List<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    public void updateList(List<Category> newList) {
        this.categories = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category, position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryBinding binding;

        CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Category category, boolean isSelected) {
            binding.categoryName.setText(category.getName());
            
            Glide.with(itemView.getContext())
                    .load(category.getImageUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.featured_placeholder)
                    .centerCrop()
                    .into(binding.categoryImage);

            if (isSelected) {
                binding.categoryCard.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primary));
                binding.categoryName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                binding.categoryCard.setStrokeWidth(0);
                binding.categoryCard.setCardElevation(8f);
            } else {
                binding.categoryCard.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.surface));
                binding.categoryName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.text_primary));
                binding.categoryCard.setStrokeWidth(2);
                binding.categoryCard.setStrokeColor(ContextCompat.getColor(itemView.getContext(), R.color.divider));
                binding.categoryCard.setCardElevation(0f);
            }

            binding.getRoot().setOnClickListener(v -> {
                int currentPos = getAdapterPosition();
                if (currentPos != RecyclerView.NO_POSITION && selectedPosition != currentPos) {
                    int previousPosition = selectedPosition;
                    selectedPosition = currentPos;
                    notifyItemChanged(previousPosition);
                    notifyItemChanged(selectedPosition);
                    listener.onCategoryClick(category);
                }
            });
        }
    }
}
