package com.tag.tai.tag.Services.Responses.GetLocations;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jam on 20-03-2018.
 */

public class LocationsResponse {

    String action;

    @SerializedName("data")
    ArrayList<LocationsData> message;

    public LocationsResponse(String action, ArrayList<LocationsData> message) {
        this.action = action;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<LocationsData> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<LocationsData> message) {
        this.message = message;
    }
}
