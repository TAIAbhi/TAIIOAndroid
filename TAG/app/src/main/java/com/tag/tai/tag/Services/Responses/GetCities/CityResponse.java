package com.tag.tai.tag.Services.Responses.GetCities;

import java.util.ArrayList;

/**
 * Created by Jam on 01-05-2018.
 */

public class CityResponse {

    String action;
    String message;
    ArrayList<CityData> data;

    public CityResponse(String action, String message, ArrayList<CityData> data) {
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

    public ArrayList<CityData> getData() {
        return data;
    }

    public void setData(ArrayList<CityData> data) {
        this.data = data;
    }
}
