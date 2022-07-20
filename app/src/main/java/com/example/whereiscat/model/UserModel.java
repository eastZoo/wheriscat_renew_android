package com.example.whereiscat.model;

public class UserModel {
    private String email,avatar,nickname;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserModel(String email, String avatar, String nickname) {
        this.email = email;
        this.avatar = avatar;
        this.nickname = nickname;
    }
}
