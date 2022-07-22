package com.example.whereiscat;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.whereiscat.Adapters.FinishedTaskAdapter;
import com.example.whereiscat.Adapters.TodoListAdapter;
import com.example.whereiscat.UtilsService.SharedPreferenceClass;
import com.example.whereiscat.interfaces.RecyclerViewClickListener;
import com.example.whereiscat.model.TodoModel;
import com.example.whereiscat.model.TodoResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FinishedTaskFragment extends Fragment implements RecyclerViewClickListener {

    SharedPreferenceClass sharedPreferenceClass;
    String token;
    FinishedTaskAdapter todoListAdapter;
    RecyclerView recyclerView;
    TextView empty_tv;
    ProgressBar progressBar;
    ArrayList<TodoModel> arrayList;

    public FinishedTaskFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finished_task, container, false);

        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        token = sharedPreferenceClass.getValue_string("token");


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

        Call<TodoResponse> todoResponseCall = ApiClient.getTodoService().getFinishTasks(token = sharedPreferenceClass.getValue_string("token"));
        todoResponseCall.enqueue(new Callback<TodoResponse>() {
            @Override
            public void onResponse(Call<TodoResponse> call, retrofit2.Response<TodoResponse> response) {
                if (response.isSuccessful()) {
                    TodoResponse todoResponse = response.body();
                    List<TodoModel> todos = todoResponse.getTodos();

                    // 서버로부터 응답받은 todos에서 하나씩 빼서 TodoModel에 저장 객체 리스트에저장 어댑터로 전송!!
                    for (int i = 0; i < todos.size(); i++) {

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

                    todoListAdapter = new FinishedTaskAdapter(getActivity(), arrayList, FinishedTaskFragment.this);
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

    private void deleteTask(final String id, final int position) {
        Call<TodoResponse> todoResponseCall = ApiClient.getTodoService().deleteTask(id);
        todoResponseCall.enqueue(new Callback<TodoResponse>() {
            @Override
            public void onResponse(Call<TodoResponse> call, Response<TodoResponse> response) {
                if (response.isSuccessful()) {
                    arrayList.remove(position);
                    todoListAdapter.notifyItemRemoved(position);
//                    response 메세지 받아서 서버로부터 받은 msg 출력하기 추가 해보기!!
//                    Toast.makeText(getActivity(), response.getString("msg"), Toast.LENGTH_SHORT).show();

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

    public void showDeleteDialog(final String id, final int position) {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("완료한 투두를 삭제하시겠습니까?")
                .setPositiveButton("삭제", null)
                .setNegativeButton("취소", null)
                .create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteTask(id, position);
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }

    @Override
    public void onEditButtonClick(int position) {

    }

    @Override
    public void onDeleteButtonClick(int position) {
        Toast.makeText(getActivity(), "Position " + arrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
        showDeleteDialog(arrayList.get(position).getId(), position);
    }

    @Override
    public void onDoneButtonClick(int position) {

    }
}