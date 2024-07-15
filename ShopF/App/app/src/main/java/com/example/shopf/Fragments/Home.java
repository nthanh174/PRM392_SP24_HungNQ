package com.example.shopf.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopf.Activities.ProductDetailActivity;
import com.example.shopf.Adapters.CategoriesAdapter;
import com.example.shopf.Adapters.ProductsAdapter;
import com.example.shopf.R;
import com.example.shopf.Services.ApiClient;
import com.example.shopf.Services.ApiServices;
import com.example.shopf.Models.CategoryDTO;
import com.example.shopf.Models.ProductDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    private RecyclerView rcvCategoriesHot;
    private RecyclerView rcvProductHot;

    private ApiServices apiServices;

    public Home() {
        // Constructor rỗng
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiServices = ApiClient.getClient().create(ApiServices.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rcvCategoriesHot = view.findViewById(R.id.rcvCategoriesHot);
        rcvProductHot = view.findViewById(R.id.rcvProductHot);

        // Thiết lập RecyclerView
        setupRecyclerViews();

        // Tải dữ liệu từ API
        loadData();

        return view;
    }

    private void setupRecyclerViews() {
        // Thiết lập RecyclerView cho Categories
        rcvCategoriesHot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Thiết lập RecyclerView cho Products
        rcvProductHot.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadData() {
        apiServices.GetCategories().enqueue(new Callback<List<CategoryDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<CategoryDTO>> call, @NonNull Response<List<CategoryDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Xử lý khi nhận được response thành công
                    List<CategoryDTO> categories = response.body();
                    if (!categories.isEmpty()) {
                        CategoriesAdapter adapter = new CategoriesAdapter(categories);
                        rcvCategoriesHot.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "Empty category list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Xử lý khi response không thành công
                    Toast.makeText(getContext(), "Failed to get categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryDTO>> call, Throwable t) {
                // Xử lý khi gọi API thất bại
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        apiServices.GetProducts().enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Xử lý khi nhận được response thành công
                    List<ProductDTO> products = response.body();
                    if (!products.isEmpty()) {
                        ProductsAdapter adapter = new ProductsAdapter(products, new ProductsAdapter.OnProductItemClickListener() {
                            @Override
                            public void onProductItemClick(ProductDTO product) {
                                // Xử lý khi người dùng click vào sản phẩm
                                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                                intent.putExtra("productId", product.getProductId()); // Pass product ID or any other relevant data
                                startActivity(intent);
                            }
                        });
                        rcvProductHot.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "Empty product list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Xử lý khi response không thành công
                    Toast.makeText(getContext(), "Failed to get products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                // Xử lý khi gọi API thất bại
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
