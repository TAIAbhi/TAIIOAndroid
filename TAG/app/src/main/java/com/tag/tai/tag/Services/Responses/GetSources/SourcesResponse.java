package com.tag.tai.tag.Services.Responses.GetSources;

import java.util.ArrayList;

/**
 * Created by Jam on 11-04-2018.
 */

public class SourcesResponse {

    String action;
    String message;
    ArrayList<SourcesData> data;

    public SourcesResponse(String action, String message, ArrayList<SourcesData> data) {
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

    public ArrayList<SourcesData> getData() {
        return data;
    }

    public void setData(ArrayList<SourcesData> data) {
        this.data = data;
    }
}
