package com.tag.tai.tag.Services.Responses.HelpDetails;

import java.util.ArrayList;

/**
 * Created by Jam on 13-04-2018.
 */

public class HelpResponse {

    String action;
    String message;
    ArrayList<HelpData> data;

    public HelpResponse(String action, String message, ArrayList<HelpData> data) {
        this.action = action;
        this.message = message;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<HelpData> getData() {
        return data;
    }

    public void setData(ArrayList<HelpData> data) {
        this.data = data;
    }
}
