package com.example.whereiscat;

import java.util.ArrayList;

public class TodoResponse {

    private ArrayList todos;
    private String id;
    private String title;
    private String description;

    public ArrayList getTodos() {
        return todos;
    }

    public void setTodos(ArrayList todos) {
        this.todos = todos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
