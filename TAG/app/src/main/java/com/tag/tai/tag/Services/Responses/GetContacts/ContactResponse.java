package com.tag.tai.tag.Services.Responses.GetContacts;

import java.util.ArrayList;

/**
 * Created by Jam on 11-04-2018.
 */

public class ContactResponse {

    String action;
    String message;
    ArrayList<ContactData> data;

    public ContactResponse(String action, String message, ArrayList<ContactData> data) {
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

    public ArrayList<ContactData> getData() {
        return data;
    }

    public void setData(ArrayList<ContactData> data) {
        this.data = data;
    }
}
