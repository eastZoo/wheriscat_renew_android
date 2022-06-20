package com.example.whereiscat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    // 나중에 todo/auth/만 따로 빼서 apiclient 다시 만들기
    @POST("todo/auth/register/")
    Call<UserResponse> saveUser(@Body UserRequest userRequest);

    @POST("todo/auth/login/")
    Call<UserResponse> loginUser(@Body UserRequest userRequest);

}
