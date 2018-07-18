package com.tag.tai.tag.Services.Interfaces;


import com.tag.tai.tag.Services.Requests.Login.LoginRequest;
import com.tag.tai.tag.Services.Requests.SkipVideo.SkipRequest;
import com.tag.tai.tag.Services.Responses.Login.LoginResponse;
import com.tag.tai.tag.Services.Responses.SkipVideo.SkipResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Jam on 08-03-2018.
 */

public interface Login {

    @Headers("Content-Type:application/json")
    @POST("api/login")
    Call<LoginResponse> checkLogin(@Body LoginRequest loginRequest);

    @Headers("Content-Type:application/json")
    @POST("api/skipvideo")
    Call<SkipResponse> skipVideo(@Header("Token") String token, @Body SkipRequest request);

}
