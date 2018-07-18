package com.tag.tai.tag.Services.Requests.NotificationDismiss;

public class NotificationDismissRequest {

    String contactId;
    String notificationID;
    String isDone;
    String dismiss;

    public NotificationDismissRequest(String contactId, String notificationID, String isDone, String dismiss) {
        this.contactId = contactId;
        this.notificationID = notificationID;
        this.isDone = isDone;
        this.dismiss = dismiss;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }

    public String getDismiss() {
        return dismiss;
    }

    public void setDismiss(String dismiss) {
        this.dismiss = dismiss;
    }
}
