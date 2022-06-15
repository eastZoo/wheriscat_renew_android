package com.example.whereiscat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whereiscat.UtilsService.SharedPreferenceClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button whereisCatBtn, todoListBtn,logoutBtn;
    SharedPreferenceClass sharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        whereisCatBtn = findViewById(R.id.whereisCatBtn);
        todoListBtn = findViewById(R.id.todoListBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        sharedPreferenceClass = new SharedPreferenceClass(this);

        // 로그아웃 버튼
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferenceClass.clear();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        // 어디있냥 앱 실행 버튼
        whereisCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CatActivity.class));
                finish();
            }
        });

        // Todolist 앱 실행 버튼
        todoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TodoActivity.class));
                finish();
            }
        });

    }
}