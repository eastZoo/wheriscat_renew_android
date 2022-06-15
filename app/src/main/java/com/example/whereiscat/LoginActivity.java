package com.example.whereiscat;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whereiscat.UtilsService.SharedPreferenceClass;
import com.example.whereiscat.UtilsService.UtilService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button registerBtn;

    private Button  loginBtn;
    private EditText email_ET, password_ET;

    ProgressBar progressBar;
    UtilService utilService;
    SharedPreferenceClass sharedPreferenceClass;

    private String email_s, password_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_ET = findViewById(R.id.email_ET);
        password_ET = findViewById(R.id.password_ET);

        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        utilService = new UtilService();
        registerBtn = findViewById(R.id.registerBtn);

        sharedPreferenceClass = new SharedPreferenceClass(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilService.hideKeyboard(view, LoginActivity.this);
                email_s = email_ET.getText().toString();
                password_s = password_ET.getText().toString();

                if(validate(view)){
                    loginUser(createRequest());
                }
            }
        });

    }
    public UserRequest createRequest(){
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(email_ET.getText().toString());
        userRequest.setPassword(password_ET.getText().toString());
        return userRequest;
    }


    public void loginUser(UserRequest userRequest) {

        Call<UserResponse> userResponseCall = ApiClient.getUserService().loginUser(userRequest);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    String token = userResponse.getToken();

                    sharedPreferenceClass.setValue_string("token", token);
                    Log.d(TAG, "token : " + userResponse.getToken());
                    Toast.makeText(LoginActivity.this, token, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class ));

                } else {
                    Toast.makeText(LoginActivity.this, "Request failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Request failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validate(View view) {
        boolean isValid;

            if(!TextUtils.isEmpty(email_s)) {
                if(!TextUtils.isEmpty(password_s)) {
                    isValid = true;
                } else {
                    utilService.showSnackBar(view,"please enter password....");
                    isValid = false;
                }
            } else {
                utilService.showSnackBar(view,"please enter email....");
                isValid = false;
            }
        return  isValid;
    }

    // SharedPreferences 쿠키 세션 역할!!!!
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences cat_pref = getSharedPreferences("user_cat", MODE_PRIVATE);
        // 시작시 토큰 저장된 정보가지고 있으면 바로 로그인, 웹 세션 느낌
        if(cat_pref.contains("token")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}