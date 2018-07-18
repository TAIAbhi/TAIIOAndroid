package com.tag.tai.tag.Services.Responses.GetMyDetails;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jam on 18-03-2018.
 */

public class MyDetailsResponse {

    String action;

    @SerializedName("data")
    MyDetailsData message;

    public MyDetailsResponse(String action, MyDetailsData message) {
        this.action = action;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public MyDetailsData getMessage() {
        return message;
    }

    public void setMessage(MyDetailsData message) {
        this.message = message;
    }
}
