package com.example.shopf.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopf.Models.LoginDTO;
import com.example.shopf.R;
import com.example.shopf.Services.ApiClient;
import com.example.shopf.Services.ApiServices;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwActivity extends AppCompatActivity {

    private EditText etPassword, etRePassword;
    private ApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        etPassword = findViewById(R.id.password);
        etRePassword = findViewById(R.id.rePassword);
        Button btnSave = findViewById(R.id.saveBt);

        // Khởi tạo Retrofit Service
        apiServices = ApiClient.getClient().create(ApiServices.class);

        btnSave.setOnClickListener(view -> changePassword());
    }

    private void changePassword() {
        String newPassword = etPassword.getText().toString().trim();
        String reEnteredPassword = etRePassword.getText().toString().trim();

        if (!newPassword.equals(reEnteredPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        if (username.isEmpty()) {
            Toast.makeText(this, "Session not found, please login again", Toast.LENGTH_SHORT).show();
            navigateToLogin(); // Phương thức để chuyển sang màn hình Login
        }


        LoginDTO loginDTO = new LoginDTO(username, newPassword); // LoginDTO phải có thêm newPassword

        Call<ResponseBody> call = apiServices.ChangePassword(loginDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChangePwActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng activity sau khi thay đổi mật khẩu thành công
                } else {
                    Toast.makeText(ChangePwActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(ChangePwActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(ChangePwActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Đóng activity hiện tại sau khi chuyển sang màn hình Login
    }

}
