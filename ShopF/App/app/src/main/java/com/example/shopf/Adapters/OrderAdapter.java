package com.example.shopf.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopf.Models.OrderDTO;
import com.example.shopf.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderDTO> orderList;
    private OnOrderItemClickListener listener;

    public OrderAdapter(List<OrderDTO> orderList, OnOrderItemClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderDTO orderDTO = orderList.get(position);
        holder.bind(orderDTO, listener);
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUserName;
        private TextView txtAddress;
        private TextView totalItem;
        private TextView totalPriceOrder;
        private Button btnViewAll;
        private Button btnCancel;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            totalItem = itemView.findViewById(R.id.total_item);
            totalPriceOrder = itemView.findViewById(R.id.total_price_order);
            btnViewAll = itemView.findViewById(R.id.btnViewAll);
            btnCancel = itemView.findViewById(R.id.btnCancer);
        }

        public void bind(OrderDTO orderDTO, OnOrderItemClickListener listener) {
            txtUserName.setText("User: " + orderDTO.getUserId());
            txtAddress.setText("Address: " + orderDTO.getOrderAddress());
            totalItem.setText("Total item: " + orderDTO.getOrderDetails().size());
            totalPriceOrder.setText("Total Price: $" + orderDTO.getTotal());

            btnViewAll.setOnClickListener(v -> listener.onViewAllButtonClick(orderDTO));
            btnCancel.setOnClickListener(v -> listener.onCancelOrderButtonClick(orderDTO));
        }
    }

    // Interface for item click events
    public interface OnOrderItemClickListener {
        void onViewAllButtonClick(OrderDTO orderDTO);

        void onCancelOrderButtonClick(OrderDTO orderDTO);
    }
}