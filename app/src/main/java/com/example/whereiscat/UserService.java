package com.example.whereiscat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("register/")
    Call<UserResponse> saveUser(@Body UserRequest userRequest);

    @POST("login/")
    Call<UserResponse> loginUser(@Body UserRequest userRequest);
}
