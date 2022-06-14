package com.example.whereiscat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whereiscat.UtilsService.UtilService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button registerBtn;
    private EditText nickname, email, password;

    private String nickname_sb, email_sb, password_sb;
    ProgressBar progressBar;
    UtilService utilService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nickname = findViewById(R.id.nickname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        registerBtn = findViewById(R.id.registerBtn);

        utilService = new UtilService();

        registerBtn.setOnClickListener((view) -> {
            utilService.hideKeyboard(view, RegisterActivity.this);
            nickname_sb = nickname.getText().toString();
            email_sb = email.getText().toString();
            password_sb = password.getText().toString();
            if(validate(view)){
                saveUser(createRequest());
            }
        });
    }

    public boolean validate(View view) {
        boolean isValid;

        if(!TextUtils.isEmpty(nickname_sb)) {
            if(!TextUtils.isEmpty(email_sb)) {
                if(!TextUtils.isEmpty(password_sb)) {
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

    public UserRequest createRequest(){
        UserRequest userRequest = new UserRequest();
        userRequest.setNickname(nickname.getText().toString());
        userRequest.setEmail(email.getText().toString());
        userRequest.setPassword(password.getText().toString());


        return userRequest;
    }

    public void saveUser(UserRequest userRequest){

        Call<UserResponse> userResponseCall = ApiClient.getUserService().saveUser(userRequest);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Save successfully", Toast.LENGTH_LONG).show();
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
}