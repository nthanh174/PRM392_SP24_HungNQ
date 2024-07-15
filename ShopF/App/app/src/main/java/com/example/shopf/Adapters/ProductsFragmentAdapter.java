package com.example.shopf.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopf.Models.ProductDTO;
import com.example.shopf.R;

import java.util.List;

public class ProductsFragmentAdapter extends RecyclerView.Adapter<ProductsFragmentAdapter.ProductViewHolder> {

    private List<ProductDTO> products;
    private final OnProductItemClickListener listener;

    public ProductsFragmentAdapter(List<ProductDTO> products, OnProductItemClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_product_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductDTO product = products.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvProductName;
        private final TextView tvPrice;
        private final TextView btnAdd;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }

        public void bind(final ProductDTO product, final OnProductItemClickListener listener) {
            tvProductName.setText(product.getProductName());
            tvPrice.setText(String.valueOf(product.getPrice()));

            // Handle click events
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onProductItemClick(product);
                }
            });
        }
    }

    public interface OnProductItemClickListener {
        void onProductItemClick(ProductDTO product);
    }
}
