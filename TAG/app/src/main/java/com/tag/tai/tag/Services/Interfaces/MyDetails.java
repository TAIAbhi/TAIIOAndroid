package com.tag.tai.tag.Services.Interfaces;

import com.tag.tai.tag.Services.Requests.AddContact.AddContactRequest;
import com.tag.tai.tag.Services.Requests.UpdateDetails.UpdateDetailsRequest;
import com.tag.tai.tag.Services.Responses.AddContactResponse.AddContactResponse;
import com.tag.tai.tag.Services.Responses.GetMyDetails.MyDetailsResponse;
import com.tag.tai.tag.Services.Responses.UpdateDetails.UpdetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Jam on 18-03-2018.
 */

public interface MyDetails {

    @GET("api/me?platform=2")
    Call<MyDetailsResponse> getMyDetails(@Header("Token") String token,@Query("contactId") String contactid);

    @Headers("Content-Type:application/json")
    @PUT("api/me")
    Call<UpdetailsResponse> updateMyDetails(@Header("Token") String token, @Body UpdateDetailsRequest request);

    @Headers("Content-Type:application/json")
    @POST("api/me")
    Call<AddContactResponse> addContact(@Header("Token") String token, @Body AddContactRequest request);

}
