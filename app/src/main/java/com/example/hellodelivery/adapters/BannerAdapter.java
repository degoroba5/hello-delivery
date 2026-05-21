package com.example.hellodelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hellodelivery.databinding.ItemBannerBinding;
import com.example.hellodelivery.models.Banner;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private List<Banner> bannerList;

    public BannerAdapter(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public void updateList(List<Banner> newList) {
        this.bannerList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBannerBinding binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BannerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        Banner banner = bannerList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(banner.getImageUrl())
                .into(holder.binding.bannerImage);
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ItemBannerBinding binding;
        BannerViewHolder(ItemBannerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
