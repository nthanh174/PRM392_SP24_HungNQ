package com.example.shopf.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopf.Activities.ProductDetailActivity;
import com.example.shopf.Adapters.ProductsFragmentAdapter;
import com.example.shopf.Models.ProductDTO;
import com.example.shopf.R;
import com.example.shopf.Services.ApiClient;
import com.example.shopf.Services.ApiServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends Fragment {

    private RecyclerView rcvAllProduct;
    private ApiServices apiServices;

    public Product() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiServices = ApiClient.getClient().create(ApiServices.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        rcvAllProduct = view.findViewById(R.id.rcvAllProduct);

        // Setup RecyclerView
        setupRecyclerView();

        // Load data from API
        loadData();

        return view;
    }

    private void setupRecyclerView() {
        // Setup RecyclerView for Products
        rcvAllProduct.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadData() {
        apiServices.GetProducts().enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle successful response
                    List<ProductDTO> products = response.body();
                    if (!products.isEmpty()) {
                        ProductsFragmentAdapter adapter = new ProductsFragmentAdapter(products, new ProductsFragmentAdapter.OnProductItemClickListener() {
                            @Override
                            public void onProductItemClick(ProductDTO product) {
                                // Handle item click
                                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                                intent.putExtra("product_id", product.getProductId());
                                startActivity(intent);
                            }
                        });
                        rcvAllProduct.setAdapter(adapter);
                    } else {
                        // Handle empty product list
                        Toast.makeText(getContext(), "No products found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(getContext(), "Failed to load products.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                // Handle request failure
                Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
