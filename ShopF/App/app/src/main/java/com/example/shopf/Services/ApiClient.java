package com.example.shopf.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Base URL của API
    private static final String BASE_URL = "http://10.0.2.2:5000/api/User/";
    private static Retrofit retrofit = null;

    // Phương thức để lấy Retrofit client
    public static Retrofit getClient() {
        // Kiểm tra nếu retrofit đã được khởi tạo hay chưa
        if (retrofit == null) {
            // Khởi tạo Retrofit instance với base URL
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson Converter để chuyển đổi JSON thành đối tượng Java
                    .build();
        }
        return retrofit;
    }
}
