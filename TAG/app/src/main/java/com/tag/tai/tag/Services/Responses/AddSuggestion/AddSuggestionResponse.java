package com.tag.tai.tag.Services.Responses.AddSuggestion;

/**
 * Created by Jam on 21-03-2018.
 */

public class AddSuggestionResponse {

    String action;
    String message;

    public AddSuggestionResponse(String action, String message) {
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
