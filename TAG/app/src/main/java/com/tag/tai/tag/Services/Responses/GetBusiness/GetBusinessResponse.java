package com.tag.tai.tag.Services.Responses.GetBusiness;

import java.util.ArrayList;

/**
 * Created by Jam on 11-04-2018.
 */

public class GetBusinessResponse {

    String action;
    String message;
    ArrayList<BusinessData> data;

    public GetBusinessResponse(String action, String message, ArrayList<BusinessData> data) {
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

    public ArrayList<BusinessData> getData() {
        return data;
    }

    public void setData(ArrayList<BusinessData> data) {
        this.data = data;
    }

}
