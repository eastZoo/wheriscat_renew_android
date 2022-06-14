package com.example.whereiscat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.whereiscat.UtilsService.UtilService;

public class LoginActivity extends AppCompatActivity {
    private Button registerBtn;

    private Button  loginBtn;
    private EditText email_ET, password_ET;

    ProgressBar progressBar;
    UtilService utilService;

    private String nickname_s, email_s, password_s;
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

    private void loginUser(View view) {

    }

    private boolean validate(View view) {
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