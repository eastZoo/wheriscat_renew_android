package com.example.whereiscat;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.whereiscat.UtilsService.UtilService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Button loginBtn, registerBtn;
    private EditText nickname_ET, email_ET, password_ET;
    ProgressBar progressBar;

    private String nickname, email, password;
    UtilService utilService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginBtn = findViewById(R.id.loginBtn);
        nickname_ET = findViewById(R.id.name_ET);
        email_ET = findViewById(R.id.email_ET);
        password_ET = findViewById(R.id.password_ET);
        progressBar = findViewById(R.id.progress_bar);
        registerBtn = findViewById(R.id.registerBtn);

        utilService = new UtilService();
        loginBtn.setOnClickListener((view) -> {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
        });

        registerBtn.setOnClickListener((view) -> {
            utilService.hideKeyboard(view, RegisterActivity.this);
            nickname = nickname_ET.getText().toString();
            email = email_ET.getText().toString();
            password = password_ET.getText().toString();
            Log.e(TAG, nickname);
            Log.e(TAG, email);
            Log.e(TAG, password);
            if(validate(view)){
                registerUser(view);
            }
        });
    }

    private void registerUser(View view) {
        progressBar.setVisibility(View.VISIBLE);

        //암호화 하기 위해 해쉬맵에 담아서 사용
        HashMap<String, Object> postingData = new HashMap<String, Object>();
        postingData.put("nickname", nickname);
        postingData.put("email", email);
        postingData.put("password", password);

        //클라이언트에 인스턴스를
        Methods methods = Client.getRetrofitInstance().create(Methods.class);
        methods.postData(postingData, "register").enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(response.isSuccessful()){
                    Model model = response.body();
                    Log.e(TAG, "POST" + "post 성공");
                    Log.e(TAG, "POST" + model.getUser_token());
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }

    public boolean validate(View view) {
        boolean isValid;

        if(!TextUtils.isEmpty(nickname)) {
            if(!TextUtils.isEmpty(email)) {
                if(!TextUtils.isEmpty(password)) {
                    isValid = true;
                } else {
                    utilService.showSnackBar(view,"please enter password....");
                    isValid = false;
                }
            } else {
                utilService.showSnackBar(view,"please enter email....");
                isValid = false;
            }
        } else {
            utilService.showSnackBar(view,"please enter name....");
            isValid = false;
        }

        return  isValid;
    }
}