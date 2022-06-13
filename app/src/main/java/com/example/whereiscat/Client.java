package com.example.whereiscat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Retrofit retrofit;
    //서버 주소 중 바뀌지 않는 부분 = baseUrl
    private static String baseUrl = "http://10.0.2.2:3000/api/todo/auth/";

    //레트로핏 인스턴스 생성
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null ) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()) //Gson -> json 변환기 생성
                    .build();
        }
        return retrofit;
    }
}
