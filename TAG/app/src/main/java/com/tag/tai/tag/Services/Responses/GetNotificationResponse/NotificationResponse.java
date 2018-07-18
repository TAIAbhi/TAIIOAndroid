package com.tag.tai.tag.Services.Responses.GetNotificationResponse;

import java.util.ArrayList;

public class NotificationResponse {

        String action;
        String message;
        ArrayList<Notification> data;

    public NotificationResponse(String action, String message, ArrayList<Notification> data) {
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

    public ArrayList<Notification> getData() {
        return data;
    }

    public void setData(ArrayList<Notification> data) {
        this.data = data;
    }
}
