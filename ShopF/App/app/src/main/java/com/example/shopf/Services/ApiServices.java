package com.example.shopf.Services;

import com.example.shopf.Models.CategoryDTO;
import com.example.shopf.Models.LoginDTO;
import com.example.shopf.Models.OrderDTO;
import com.example.shopf.Models.ProductDTO;
import com.example.shopf.Models.RegisterDTO;
import com.example.shopf.Models.UserDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServices {
    @POST("Login")
    Call<ResponseBody> login(@Body LoginDTO loginDTO);

    @POST("Register")
    Call<ResponseBody> register(@Body RegisterDTO registerDTO);

    @POST("ChangePassword")
    Call<ResponseBody> ChangePassword(@Body LoginDTO loginDTO);

    @GET("Products")
    Call<List<ProductDTO>> GetProducts();

    @GET("Categories")
    Call<List<CategoryDTO>> GetCategories();

    @GET("GetProductById/{id}")
    Call<ProductDTO> getProductById(@Path("id") int id);

    @GET("GetUserByUserName/{username}")
    Call<UserDTO> getUserByUserName(@Path("username") String username);

    @GET("GetOrderByUserId/{id}")
    Call<List<OrderDTO>> GetOrderByUserId(@Path("id") int id);
}

