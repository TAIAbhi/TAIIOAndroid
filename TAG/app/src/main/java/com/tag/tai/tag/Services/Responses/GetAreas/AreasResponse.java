package com.tag.tai.tag.Services.Responses.GetAreas;

import java.util.ArrayList;

public class AreasResponse {

    String action;
    String message;
    ArrayList<AreaData> data;

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

    public ArrayList<AreaData> getData() {
        return data;
    }

    public void setData(ArrayList<AreaData> data) {
        this.data = data;
    }
}
