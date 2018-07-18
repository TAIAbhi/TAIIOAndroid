package com.tag.tai.tag.Services.Responses.UpdateDetails;

/**
 * Created by Jam on 22-03-2018.
 */

public class UpdetailsResponse {

    String action;
    String message;

    public UpdetailsResponse(String action, String message) {
        this.action = action;
        this.message = message;
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
}
