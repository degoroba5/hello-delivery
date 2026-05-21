package com.example.hellodelivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> orders;
    private OnReorderClickListener reorderClickListener;

    public interface OnReorderClickListener {
        void onReorderClick(Order order);
    }

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    public void setOnReorderClickListener(OnReorderClickListener listener) {
        this.reorderClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderIdTextView.setText(order.getId());
        holder.orderDateTextView.setText(order.getDate());
        
        String itemsText = order.getItems().stream()
                .map(DeliveryItem::getName)
                .collect(Collectors.joining(", "));
        holder.orderItemsTextView.setText(itemsText);
        
        holder.orderTotalTextView.setText(String.format(Locale.getDefault(), "%.2f ETB", order.getTotalAmount()));

        holder.reorderButton.setOnClickListener(v -> {
            if (reorderClickListener != null) {
                reorderClickListener.onReorderClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView orderDateTextView;
        TextView orderItemsTextView;
        TextView orderTotalTextView;
        MaterialButton reorderButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id);
            orderDateTextView = itemView.findViewById(R.id.order_date);
            orderItemsTextView = itemView.findViewById(R.id.order_items);
            orderTotalTextView = itemView.findViewById(R.id.order_total);
            reorderButton = itemView.findViewById(R.id.btn_reorder);
        }
    }
}