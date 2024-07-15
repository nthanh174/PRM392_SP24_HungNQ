package com.example.shopf.Activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopf.Models.UserDTO;
import com.example.shopf.R;
import com.example.shopf.Services.ApiClient;
import com.example.shopf.Services.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText dobEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Initialize views
        emailEditText = findViewById(R.id.emailAM);
        phoneEditText = findViewById(R.id.phoneAM);
        dobEditText = findViewById(R.id.dobAM);

        // Get username passed from previous fragment/activity
        String username = getIntent().getStringExtra("username");

        // Retrofit initialization
        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);

        // Call API to get user info
        Call<UserDTO> call = apiServices.getUserByUserName(username);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserDTO userDTO = response.body();

                    // Set retrieved user info to UI elements
                    emailEditText.setText(userDTO.getEmail());
                    phoneEditText.setText(userDTO.getPhone());
                    // Assuming dobEditText is a Date field, you may need to format it appropriately
                    dobEditText.setText(userDTO.getDob().toString());  // Example, format as per your need
                } else {
                    // Handle API call failure or empty response
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                // Handle API call failure
            }
        });
    }
}
