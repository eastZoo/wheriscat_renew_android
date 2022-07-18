package com.example.whereiscat.model;

import com.example.whereiscat.model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class TodoResponse {

    private String suuccess;
    private int count;
    private List<TodoModel> todos;

    public String getSuccess() {
        return suuccess;
    }

    public void setSuccess(String suuccess) {
        this.suuccess = suuccess;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTodos(List<TodoModel> todos) {
        this.todos = todos;
    }

    public List<TodoModel> getTodos() {
        return todos;
    }
}
