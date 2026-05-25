package com.example.hellodelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hellodelivery.R;
import com.example.hellodelivery.databinding.ItemReviewBinding;
import com.example.hellodelivery.models.Review;
import java.util.List;
import java.util.Locale;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public void updateList(List<Review> newList) {
        this.reviewList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReviewBinding binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.binding.userName.setText(review.getUserName());
        holder.binding.reviewDate.setText(review.getDate());
        // Fix: Changed reviewComment to reviewContent to match the ID in item_review.xml
        holder.binding.reviewContent.setText(review.getComment());
        // Fix: Changed reviewRating.setRating to ratingText.setText to match the layout
        holder.binding.ratingText.setText(String.format(Locale.getDefault(), "%.1f", review.getRating()));

        Glide.with(holder.itemView.getContext())
                .load(review.getUserImageUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.binding.userAvatar); // Fix: Changed userImage to userAvatar
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ItemReviewBinding binding;
        ReviewViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
