package com.example.whereiscat.model;
import com.example.whereiscat.model.UserModel;
import java.util.List;

public class UserResponse {
    private String success;
    private String token;
    private String msg;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
