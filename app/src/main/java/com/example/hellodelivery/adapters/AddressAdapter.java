package com.example.hellodelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hellodelivery.database.AddressEntity;
import com.example.hellodelivery.databinding.ItemAddressBinding;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<AddressEntity> addressList;
    private final OnAddressClickListener listener;

    public interface OnAddressClickListener {
        void onAddressClick(AddressEntity address);
        void onDeleteClick(AddressEntity address);
    }

    public AddressAdapter(List<AddressEntity> addressList, OnAddressClickListener listener) {
        this.addressList = addressList;
        this.listener = listener;
    }

    public void updateList(List<AddressEntity> newList) {
        this.addressList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddressBinding binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AddressViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressEntity address = addressList.get(position);
        holder.binding.tvAddressTitle.setText(address.getAddressTitle());
        holder.binding.tvFullAddress.setText(address.getFullAddress());

        holder.itemView.setOnClickListener(v -> listener.onAddressClick(address));
        holder.binding.btnDeleteAddress.setOnClickListener(v -> listener.onDeleteClick(address));
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder {
        ItemAddressBinding binding;
        AddressViewHolder(ItemAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
