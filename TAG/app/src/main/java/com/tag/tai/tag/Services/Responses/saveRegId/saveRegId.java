package com.tag.tai.tag.Services.Responses.saveRegId;

/**
 * Created by Jam on 23-04-2018.
 */

public class saveRegId {
    String action;
    String message;
    String uid;


    public saveRegId(String action, String message, String uid) {
        this.action = action;
        this.message = message;
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
