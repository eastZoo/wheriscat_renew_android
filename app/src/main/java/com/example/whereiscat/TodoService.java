package com.example.whereiscat;

import com.example.whereiscat.model.TodoRequest;
import com.example.whereiscat.model.TodoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TodoService {

    @POST("todo/")
    Call<TodoResponse> saveTodo(@Body TodoRequest todoRequest, @Header("Authorization") String token);

    @GET("todo/")
    Call<TodoResponse> getTasks(@Header("Authorization") String token);
}
