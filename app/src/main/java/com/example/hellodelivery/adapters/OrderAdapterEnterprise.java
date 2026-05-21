package com.example.hellodelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hellodelivery.databinding.ItemOrderEnterpriseBinding;
import com.example.hellodelivery.models.Order;
import java.util.List;
import java.util.Locale;

public class OrderAdapterEnterprise extends RecyclerView.Adapter<OrderAdapterEnterprise.OrderViewHolder> {

    private List<Order> orderList;
    private final OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
        void onReorderClick(Order order);
    }

    public OrderAdapterEnterprise(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    public void updateList(List<Order> newList) {
        this.orderList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderEnterpriseBinding binding = ItemOrderEnterpriseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.binding.orderId.setText(String.format("Order #%s", order.getId().substring(order.getId().length() - 5).toUpperCase()));
        holder.binding.orderStatus.setText(order.getStatus());
        holder.binding.orderDate.setText(order.getCreatedAt());
        holder.binding.orderTotal.setText(String.format(Locale.getDefault(), "$%.2f", order.getTotalAmount()));

        // Displaying first few items
        holder.binding.itemsContainer.removeAllViews();
        if (order.getItems() != null) {
            int count = 0;
            for (Order.OrderItem item : order.getItems()) {
                if (count >= 2) break;
                TextView tv = new TextView(holder.itemView.getContext());
                tv.setText(String.format(Locale.getDefault(), "%dx %s", item.getQuantity(), item.getName()));
                tv.setTextSize(14);
                holder.binding.itemsContainer.addView(tv);
                count++;
            }
            if (order.getItems().size() > 2) {
                TextView tv = new TextView(holder.itemView.getContext());
                tv.setText(String.format(Locale.getDefault(), "+ %d more items", order.getItems().size() - 2));
                tv.setTextSize(12);
                holder.binding.itemsContainer.addView(tv);
            }
        }

        holder.itemView.setOnClickListener(v -> listener.onOrderClick(order));
        holder.binding.btnReorder.setOnClickListener(v -> listener.onReorderClick(order));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ItemOrderEnterpriseBinding binding;
        OrderViewHolder(ItemOrderEnterpriseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
