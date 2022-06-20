package com.example.whereiscat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TodoService {

    @POST("todo/")
    Call<TodoResponse> saveTodo(@Body TodoRequest todoRequest, @Header("Authorization") String token);

}
