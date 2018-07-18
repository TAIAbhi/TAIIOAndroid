package com.tag.tai.tag.Services.Responses.GetNotificationResponse;

public class Notification {

        String notificationID;
        String notificationType;
        String notificationTitle;
        String description;
        String notificationPhoto;
        String timeSent;
        NotificationData data;

    public Notification(String notificationID, String notificationType, String notificationTitle, String description, String notificationPhoto, String timeSent, NotificationData data) {
        this.notificationID = notificationID;
        this.notificationType = notificationType;
        this.notificationTitle = notificationTitle;
        this.description = description;
        this.notificationPhoto = notificationPhoto;
        this.timeSent = timeSent;
        this.data = data;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotificationPhoto() {
        return notificationPhoto;
    }

    public void setNotificationPhoto(String notificationPhoto) {
        this.notificationPhoto = notificationPhoto;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }
}
