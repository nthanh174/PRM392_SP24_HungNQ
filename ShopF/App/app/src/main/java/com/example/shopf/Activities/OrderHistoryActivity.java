package com.example.shopf.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopf.Adapters.OrderAdapter;
import com.example.shopf.Adapters.ProductsFragmentAdapter;
import com.example.shopf.Models.OrderDTO;
import com.example.shopf.Models.ProductDTO;
import com.example.shopf.R;
import com.example.shopf.Services.ApiClient;
import com.example.shopf.Services.ApiServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<OrderDTO> orderDTOList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.rcv_order_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderDTOList, new OrderAdapter.OnOrderItemClickListener() {
            @Override
            public void onViewAllButtonClick(OrderDTO orderDTO) {
                // Handle view all button click
            }

            @Override
            public void onCancelOrderButtonClick(OrderDTO orderDTO) {
                // Handle cancel order button click
            }
        });
        recyclerView.setAdapter(orderAdapter);

        // Fetch orders from API
        fetchOrders();
    }

    private void fetchOrders() {
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        Call<List<OrderDTO>> call = apiServices.GetOrderByUserId(2); // Replace with actual user ID

        call.enqueue(new Callback<List<OrderDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                if (response.isSuccessful()) {
                    List<OrderDTO> fetchedOrders = response.body();
                    if (fetchedOrders != null && !fetchedOrders.isEmpty()) {
                        orderDTOList.clear();
                        orderDTOList.addAll(fetchedOrders);
                        orderAdapter.notifyDataSetChanged(); // Notify adapter of data change
                    } else {
                        Toast.makeText(OrderHistoryActivity.this, "No orders found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("OrderHistoryActivity", "Failed to fetch orders: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<OrderDTO>> call, Throwable t) {
                Log.e("OrderHistoryActivity", "Failed to fetch orders: " + t.getMessage());
            }
        });
    }
}

