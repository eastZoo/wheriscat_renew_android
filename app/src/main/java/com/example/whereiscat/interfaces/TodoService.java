package com.example.whereiscat.interfaces;

import com.example.whereiscat.model.TodoRequest;
import com.example.whereiscat.model.TodoResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TodoService {

    @POST("todo/")
    Call<TodoResponse> saveTodo(@Body TodoRequest todoRequest, @Header("Authorization") String token);

    @GET("todo/")
    Call<TodoResponse> getTasks(@Header("Authorization") String token);

    @GET("todo/finished")
    Call<TodoResponse> getFinishTasks(@Header("Authorization") String token);

    @PUT("todo/{id}")
    Call<TodoResponse> updateTask(@Path("id") String id, @Body HashMap body);

    @PUT("todo/{id}")
    Call<TodoResponse> updateFinish(@Path("id") String id, @Body HashMap body);

    @DELETE("todo/{id}")
    Call<TodoResponse> deleteTask(@Path("id") String id );


}
