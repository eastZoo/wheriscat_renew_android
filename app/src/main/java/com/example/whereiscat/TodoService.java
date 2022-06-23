package com.example.whereiscat;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TodoService {

    @POST("todo/")
    Call<TodoResponse> saveTodo(@Body TodoRequest todoRequest, @Header("Authorization") String token);

    @GET("todo/")
    Call<JSONObject> getTasks(@Header("Authorization") String token);
}
