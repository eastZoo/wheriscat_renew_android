package com.example.whereiscat;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whereiscat.Adapters.TodoListAdapter;
import com.example.whereiscat.UtilsService.SharedPreferenceClass;
import com.example.whereiscat.model.TodoModel;
import com.example.whereiscat.model.TodoResponse;
import com.example.whereiscat.model.UserModel;
import com.example.whereiscat.model.UserResponse;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button whereisCatBtn, todoListBtn,logoutBtn;
    SharedPreferenceClass sharedPreferenceClass;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private TextView user_name, user_email;
    private CircleImageView userImage;

    //저장된 토큰 담을 변수 선언
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        whereisCatBtn = findViewById(R.id.whereisCatBtn);
//        todoListBtn = findViewById(R.id.todoListBtn);
//        logoutBtn = findViewById(R.id.logoutBtn);
        sharedPreferenceClass = new SharedPreferenceClass(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 프로필 로그인 유저 네임 띄우기
        View hdView = navigationView.getHeaderView(0);
        user_name = (TextView) hdView.findViewById(R.id.username);
        user_email = (TextView) hdView.findViewById(R.id.user_email);
        userImage = (CircleImageView) hdView.findViewById(R.id.avatar);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setDrawerClick(item.getItemId());
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
        initDrawer();

        getUserProfile();

    }

    private void getUserProfile() {
        Call<UserResponse> userResponseCall = ApiClient.getUserService().getProfile(token = sharedPreferenceClass.getValue_string("token"));
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    List<UserModel> user = userResponse.getUser();
                    Log.e(TAG, "Profile!!! : " + response.body().getUser());

                } else {
                    Toast.makeText(MainActivity.this, "Request failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "GetProfile Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initDrawer() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.content, new HomeFragment());
        ft.commit();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    // navigationbar 화면전환 세팅
    private void setDrawerClick(int itemId) {
        switch (itemId) {
            case R.id.action_finished_task:
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new FinishedTaskFragment()).commit();
                break;
            case R.id.action_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new HomeFragment()).commit();
                break;
            case R.id.action_logout:
                sharedPreferenceClass.clear();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}