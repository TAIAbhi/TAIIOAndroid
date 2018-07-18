package com.tag.tai.tag.Services.Interfaces;

import com.tag.tai.tag.Services.Requests.AddLocation.AddLocationRequest;
import com.tag.tai.tag.Services.Responses.AddLocation.AddLocationResponse;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsResponse;
import com.tag.tai.tag.Services.Responses.GetSuburbs.SuburbsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Jam on 20-03-2018.
 */

public interface Location {

    @GET("api/suburbs")
    Call<SuburbsResponse> getSuburbs(@Header("Token") String token,@Query("cityId") String cityid);

    @GET("api/location")
    Call<LocationsResponse> getLocations(@Header("Token") String token, @Query("query") String location, @Query("cityId") String cityId);

    @POST("api/location")
    Call<AddLocationResponse> addLocation(@Header("Token") String token, @Body AddLocationRequest locationData);

}
