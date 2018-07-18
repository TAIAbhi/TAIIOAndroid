package com.tag.tai.tag.Services.Responses.SubCategories;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jam on 18-03-2018.
 */

public class SubCatResponse {

    String action;

    @SerializedName("data")
    ArrayList<SubCatData> message;

    public SubCatResponse(String action, ArrayList<SubCatData> message) {
        this.action = action;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<SubCatData> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<SubCatData> message) {
        this.message = message;
    }
}
