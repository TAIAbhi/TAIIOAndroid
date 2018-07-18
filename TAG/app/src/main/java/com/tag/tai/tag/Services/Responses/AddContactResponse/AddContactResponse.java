package com.tag.tai.tag.Services.Responses.AddContactResponse;

public class AddContactResponse {

    String action;
    String message;

    public AddContactResponse(String action, String message) {
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
