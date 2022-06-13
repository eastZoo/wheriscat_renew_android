package com.example.whereiscat;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Methods {
    //데이터 받아오기
    @Headers({"Content-Type", "application/json"})
    @GET("{id}")
    Call<ArrayList<Model>> getData(@Path("id") String id);

    //데이터 보내기
    @FormUrlEncoded
    @POST("{id}")
    Call<Model> postData(@FieldMap HashMap<String, Object> param,@Path("id") String id);

    //데이터 수정하기
    @FormUrlEncoded
    @PATCH("{id}")
    Call<Model> patchData(@FieldMap HashMap<String, Object> param ,@Path("id") String id);

    //데이터 삭제하기
    @DELETE("{id}")
    Call<Void> deleteData(@Path("id") String id);
}
