package com.tag.tai.tag.Services.Interfaces;

import com.tag.tai.tag.Services.Requests.Notification.SaveRegistrationId;
import com.tag.tai.tag.Services.Requests.NotificationDismiss.NotificationDismissRequest;
import com.tag.tai.tag.Services.Responses.GetNotificationResponse.NotificationResponse;
import com.tag.tai.tag.Services.Responses.UpdateNotification.UpdateNotificationData;
import com.tag.tai.tag.Services.Responses.saveRegId.saveRegId;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Jam on 23-04-2018.
 */

public interface Notifications {

    @GET("api/getnotifications")
    Call<NotificationResponse> getNotifications(@Header("Token") String token, @Query("contactId") String contactId, @Query("deviceId") String deviceId);

    @Headers("Content-Type:application/json")
    @POST("api/registerdevice")
    Call<saveRegId> saveRegId(@Header("Token") String token, @Body SaveRegistrationId data);


    @Headers("Content-Type:application/json")
    @PUT("api/updatenotification")
    Call<UpdateNotificationData> updateNotification(@Header("Token") String token, @Body NotificationDismissRequest data);

}
