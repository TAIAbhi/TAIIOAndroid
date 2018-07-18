package com.tag.tai.tag.Fragments.Notification;

import com.tag.tai.tag.Services.Responses.GetNotificationResponse.Notification;

public interface NotificationListeners{

    void notificationDeleted(int position, Notification notification);

    void notificationClicked(int position, Notification notification);


}
