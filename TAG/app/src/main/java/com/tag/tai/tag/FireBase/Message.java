package com.tag.tai.tag.FireBase;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tag.tai.tag.Activities.MainActivity;
import com.tag.tai.tag.R;

import static com.tag.tai.tag.Services.RetroClient.TAG;

/**
 * Created by Jam on 22-04-2018.
 */

public class Message extends FirebaseMessagingService{

    public static final String NOTIFICATION_UPDATED = "notification_updated";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "2")
                    .setSmallIcon(R.drawable.tagicon)
                    .setContentTitle("My notification 2")
                    .setContentText("Much longer text that cannot fit one line... 2")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            mBuilder.build();
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.tagicon)
                    .setContentTitle("My notification")
                    .setContentText("Much longer text that cannot fit one line...")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            mBuilder.build();

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Intent intent = new Intent(NOTIFICATION_UPDATED);
        intent.putExtra("added",true);

        LocalBroadcastManager.getInstance(getBaseContext())
                .sendBroadcast(intent);
        Log.d(TAG, "onMessageReceived: sengin a broadcakd");

    }
}
