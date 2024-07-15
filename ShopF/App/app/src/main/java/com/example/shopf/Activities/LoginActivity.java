package com.example.shopf.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopf.Models.LoginDTO;
import com.example.shopf.R;
import com.example.shopf.Services.ApiClient;
import com.example.shopf.Services.ApiServices;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private ApiServices apiServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Clear SharedPreferences
        clearSharedPreferences();

        // Ánh xạ views
        etUsername = findViewById(R.id.login_name);
        etPassword = findViewById(R.id.login_pass);
        Button btnLogin = findViewById(R.id.BtnLogin);
        TextView registerRedirect = findViewById(R.id.registerRedirect);

        // Khởi tạo Retrofit Service
        apiServices = ApiClient.getClient().create(ApiServices.class);


        // Xử lý sự kiện khi nhấn nút Login
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            LoginDTO loginDTO = new LoginDTO(username, password);
            // Gọi API đăng nhập
            Call<ResponseBody> call = apiServices.login(loginDTO);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        // Xử lý đăng nhập thành công

                        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", loginDTO.getUsername());
                        editor.apply();

                        navigateToHome();
                    } else {
                        // Xử lý đăng nhập thất bại
                        Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    // Xử lý lỗi khi gọi API
                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Xử lý sự kiện khi nhấn vào TextView để chuyển sang màn hình đăng ký
        registerRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Phương thức chuyển sang màn hình chính
    private void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Đóng LoginActivity để người dùng không quay lại nó khi nhấn nút Back
    }

    // Phương thức xóa SharedPreferences
    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Xóa tất cả các dữ liệu trong SharedPreferences
        editor.apply();
    }
}