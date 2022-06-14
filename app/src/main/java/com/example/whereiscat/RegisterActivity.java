package com.example.whereiscat;

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

import com.example.whereiscat.UtilsService.UtilService;
import com.squareup.picasso.Downloader;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button registerBtn, loginBtn;
    private EditText nickname_ET, email_ET, password_ET;

    ProgressBar progressBar;
    UtilService utilService;

    private String nickname_s, email_s, password_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nickname_ET = findViewById(R.id.nickname_ET);
        email_ET = findViewById(R.id.email_ET);
        password_ET = findViewById(R.id.password_ET);

        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        utilService = new UtilService();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilService.hideKeyboard(view, RegisterActivity.this);
                nickname_s = nickname_ET.getText().toString();
                email_s = email_ET.getText().toString();
                password_s = password_ET.getText().toString();

                if(validate(view)){
                    saveUser(createRequest());
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    public UserRequest createRequest(){
        UserRequest userRequest = new UserRequest();
        userRequest.setNickname(nickname_ET.getText().toString());
        userRequest.setEmail(email_ET.getText().toString());
        userRequest.setPassword(password_ET.getText().toString());
        return userRequest;
    }

    public void saveUser(UserRequest userRequest){

        Call<UserResponse> userResponseCall = ApiClient.getUserService().saveUser(userRequest);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();

                    String token = userResponse.getToken();
                    Log.d(TAG, "token : " + userResponse.getToken());
                    Toast.makeText(RegisterActivity.this, token, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class ));

                } else {
                    Toast.makeText(RegisterActivity.this, "Request failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Request failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean validate(View view) {
        boolean isValid;

        if(!TextUtils.isEmpty(nickname_s)) {
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
        } else {
            utilService.showSnackBar(view,"please enter name....");
            isValid = false;
        }

        return  isValid;
    }
}