package com.example.whereiscat.interfaces;

import com.example.whereiscat.model.UserRequest;
import com.example.whereiscat.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @GET("todo/auth/")
    Call<UserResponse> getProfile(@Header("Authorization") String token);
    // 나중에 todo/auth/만 따로 빼서 apiclient 다시 만들기
    @POST("todo/auth/register/")
    Call<UserResponse> saveUser(@Body UserRequest userRequest);

    @POST("todo/auth/login/")
    Call<UserResponse> loginUser(@Body UserRequest userRequest);

}
