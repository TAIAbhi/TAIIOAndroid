package com.tag.tai.tag.Services.Requests.Notification;

/**
 * Created by Jam on 23-04-2018.
 */

public class SaveRegistrationId {

    String contactId;
    String deviceId;
    String type;
    String token;

    public SaveRegistrationId(String contactId, String deviceId, String type, String token) {
        this.contactId = contactId;
        this.deviceId = deviceId;
        this.type = type;
        this.token = token;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
