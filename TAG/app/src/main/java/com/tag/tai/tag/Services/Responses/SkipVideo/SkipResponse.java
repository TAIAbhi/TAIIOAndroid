package com.tag.tai.tag.Services.Responses.SkipVideo;

/**
 * Created by Jam on 25-04-2018.
 */

public class SkipResponse {

    String action;
    String message;

    public SkipResponse(String action, String message) {
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
