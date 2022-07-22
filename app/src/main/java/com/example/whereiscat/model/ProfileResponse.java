package com.example.whereiscat.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class ProfileResponse {
    private String success;
    @SerializedName("user")
    private UserModel userModel;

    public ProfileResponse(String success, UserModel userModel) {
        this.success = success;
        this.userModel = userModel;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
