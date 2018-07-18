package com.tag.tai.tag.Services.Responses.GetSuburbs;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jam on 20-03-2018.
 */

public class SuburbsResponse {

    String action;
    @SerializedName("data")
    ArrayList<SuburbsData> message;

    public SuburbsResponse(String action, ArrayList<SuburbsData> message) {
        this.action = action;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<SuburbsData> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<SuburbsData> message) {
        this.message = message;
    }
}
