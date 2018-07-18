package com.tag.tai.tag.Services.Interfaces;

import com.tag.tai.tag.Services.Responses.HelpDetails.HelpResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Created by Jam on 13-04-2018.
 */

public interface Help {

    @Headers("Content-Type:application/json")
    @GET("api/help")
    Call<HelpResponse> getHelpDetails(@Header("Token") String token);

}
