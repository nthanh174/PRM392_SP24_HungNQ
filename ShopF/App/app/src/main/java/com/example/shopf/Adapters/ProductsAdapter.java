package com.example.shopf.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopf.Models.ProductDTO;
import com.example.shopf.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<ProductDTO> products;
    private final OnProductItemClickListener listener;

    public ProductsAdapter(List<ProductDTO> products, OnProductItemClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
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
/*        private final ImageView ivProductImage;*/

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProduct);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
/*            ivProductImage = itemView.findViewById(R.id.imgProduct);*/
        }

        public void bind(final ProductDTO product, final OnProductItemClickListener listener) {
            tvProductName.setText(product.getProductName());
            tvPrice.setText(String.valueOf(product.getPrice()));
            // Load image using Picasso
            /*Picasso.get().load(product.getImage()).into(ivProductImage);*/

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
