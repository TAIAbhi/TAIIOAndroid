package com.tag.tai.tag.Services.Responses.MicroCat;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jam on 20-03-2018.
 */

public class MicroCatResponse {

    String action;

    @SerializedName("data")
    ArrayList<MicroCatData> message;

    public MicroCatResponse(String action, ArrayList<MicroCatData> message) {
        this.action = action;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<MicroCatData> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<MicroCatData> message) {
        this.message = message;
    }
}
