package com.example.whereiscat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button registerBtn;
    private EditText nickname, email, password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nickname = findViewById(R.id.nickname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser(createRequest());
            }
        });
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
                    Toast.makeText(MainActivity.this, "Save successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Request failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Request failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}