package com.example.whereiscat;

import java.util.ArrayList;


//데이터 받아올 껍데기 정의
public class Model {
    String user_email;
    String user_avatar;
    String user_nickname;
    String user_pw;
    String user_token;

    public String getUser_id() {
        return user_email;
    }

    public void setUser_id(String user_id) {
        this.user_email = user_id;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getUser_name() {
        return user_email;
    }

    public void setUser_name(String user_name) {
        this.user_email = user_name;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}
