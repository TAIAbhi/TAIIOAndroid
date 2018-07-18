package com.tag.tai.tag.Services.Responses.AddLocation;

public class AddLocationResponse {

    String action;
    String message;

    public AddLocationResponse(String action, String message) {
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
