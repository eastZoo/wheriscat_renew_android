package com.example.whereiscat;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.example.whereiscat.Adapters.TodoListAdapter;
import com.example.whereiscat.UtilsService.SharedPreferenceClass;
import com.example.whereiscat.model.TodoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    SharedPreferenceClass sharedPreferenceClass;
    
    //저장된 토큰 담을 변수 선언
    String token;
    TodoListAdapter todoListAdapter;

    RecyclerView recyclerView;
    TextView empty_tv;
    ProgressBar progressBar;

    // Todo cycler view에 전달하기위한 리스트 생성
    ArrayList<TodoModel> arrayList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // frgment에서 this 안되길래 getActivity() 하니까 됨,, 뭐지? 알아보기
        sharedPreferenceClass = new SharedPreferenceClass(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        floatingActionButton = view.findViewById(R.id.add_task_btn);
//      sharedPreferenceClass에 의해 기기에 저장되어 있는 토큰 token 변수에 저장
//        token = sharedPreferenceClass.getValue_string("token");
//        sharedPreferenceClass = new SharedPreferenceClass(getContext());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        
        // 리싸이클러뷰 이용 투두 리스트 띄우기
        recyclerView = view.findViewById(R.id.recycler_view);
        empty_tv = view.findViewById(R.id.empty_tv);
        progressBar = view.findViewById(R.id.progress_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        
        getTasks();
        
        return view;
    }

    private void getTasks() {
        arrayList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);

        Call<TodoResponse> todoResponseCall = ApiClient.getTodoService().getTasks(token = sharedPreferenceClass.getValue_string("token"));
        todoResponseCall.enqueue(new Callback<TodoResponse>() {
            @Override
            public void onResponse(Call<TodoResponse> call, Response<TodoResponse> response) {
                if (response.isSuccessful()) {
                    TodoResponse todoResponse = response.body();
                    List<TodoModel> todos = todoResponse.getTodos();
                    Log.d(TAG, "todos!!! : " + todos.get(0).getTitle());

                    for( int i = 0 ; i < todos.size(); i++) {

                        TodoModel todoModel = new TodoModel(
                                todos.get(i).getId(),
                                todos.get(i).getTitle(),
                                todos.get(i).getDescription()
                        );
                        Log.d(TAG, "getId : " + todoModel.getId());
                        Log.d(TAG, "getTitle : " + todoModel.getTitle());
                        Log.d(TAG, "getDescription : " + todoModel.getDescription());
                        arrayList.add(todoModel);
                    }

                    todoListAdapter = new TodoListAdapter(getActivity(), arrayList );
                    recyclerView.setAdapter(todoListAdapter);

                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<TodoResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog_layout, null);

        final EditText title_field = alertLayout.findViewById(R.id.title);
        final EditText description_field = alertLayout.findViewById(R.id.description);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(alertLayout)
                .setTitle("Add Task")
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInter) {
                Button positiveBtn = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = title_field.getText().toString();
                        String description = description_field.getText().toString();
                        if(!TextUtils.isEmpty(title)) {
                            saveTodo(createRequest(title, description));
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Please enter title...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        dialog.show();
    }
    // header에 token 담아서 인증하고 , todo 저장하기 성공,,!!
    private void saveTodo(TodoRequest todoRequest) {
        Call<TodoResponse> todoResponseCall = ApiClient.getTodoService().saveTodo(todoRequest, token = sharedPreferenceClass.getValue_string("token"));
        todoResponseCall.enqueue(new Callback<TodoResponse>() {
            @Override
            public void onResponse(Call<TodoResponse> call, Response<TodoResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "추가했습니다!!", Toast.LENGTH_SHORT).show();
                    getTasks();

                } else {
                    Toast.makeText(getActivity(), "추가에 실패했습니다..", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TodoResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "실패했습니다..", Toast.LENGTH_LONG).show();
            }
        });
    }

    private TodoRequest createRequest(String title, String description) {
        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setTitle(title);
        todoRequest.setDescription(description);
        Log.d(TAG, "title, description : " + todoRequest);
        return todoRequest;
    }
}