package com.example.shopf.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopf.Models.ProductDTO;
import com.example.shopf.R;
import com.example.shopf.Services.ApiClient;
import com.example.shopf.Services.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

/*    private ImageView imgVPicDP;*/
    private TextView tvTitleDP, tvPriceDP, tvDescriptionDP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

/*        imgVPicDP = findViewById(R.id.imgVPicDP);*/
        tvTitleDP = findViewById(R.id.tvTitleDP);
        tvPriceDP = findViewById(R.id.tvPriceDP);
        tvDescriptionDP = findViewById(R.id.tvDescriptionDP);

        // Lấy productId từ Intent
        int productId = getIntent().getIntExtra("productId", -1);
        if (productId != -1) {
            // Gọi API để lấy thông tin chi tiết sản phẩm
            ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
            apiServices.getProductById(productId).enqueue(new Callback<ProductDTO>() {
                @Override
                public void onResponse(Call<ProductDTO> call, Response<ProductDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ProductDTO product = response.body();
                        // Hiển thị thông tin chi tiết sản phẩm
                        displayProductDetail(product);
                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Failed to get product detail", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProductDTO> call, Throwable t) {
                    Toast.makeText(ProductDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Invalid product id", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayProductDetail(ProductDTO product) {
        // Hiển thị thông tin chi tiết sản phẩm lên giao diện
/*        Picasso.get().load(product.getImage()).into(imgVPicDP);*/
        tvTitleDP.setText(product.getProductName());
        tvPriceDP.setText(String.valueOf(product.getPrice()));
        tvDescriptionDP.setText(product.getDescription());
    }
}
