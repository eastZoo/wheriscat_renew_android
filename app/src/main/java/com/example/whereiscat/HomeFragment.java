package com.example.whereiscat;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whereiscat.UtilsService.SharedPreferenceClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    SharedPreferenceClass sharedPreferenceClass;
    
    //저장된 토큰 담을 변수 선언
    String token;
    
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
        return view;
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