package com.tag.tai.tag.Services.Interfaces;

import com.tag.tai.tag.Services.Responses.GetAreas.AreasResponse;
import com.tag.tai.tag.Services.Responses.GetBusiness.GetBusinessResponse;
import com.tag.tai.tag.Services.Responses.GetCities.CityResponse;
import com.tag.tai.tag.Services.Responses.GetContacts.ContactResponse;
import com.tag.tai.tag.Services.Responses.GetSources.SourcesResponse;
import com.tag.tai.tag.Services.Responses.SubCategories.SubCatResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Jam on 11-04-2018.
 */

public interface FilterData {

    @GET("api/getbusiness")
    Call<GetBusinessResponse> getBusinesses(@Header("Token") String token, @Query("catId") String categoryId, @Query("SubCatId") String subCategoryId,@Query("cityId") String cityId);

    @GET("api/getsources")
    Call<SourcesResponse> getSources(@Header("Token") String token);

    @GET("api/getcontacts")
    Call<ContactResponse> getContacts(@Header("Token") String token);

    @GET("api/city")
    Call<CityResponse> getCities(@Header("Token") String token);

    @GET("api/bindvsfilterdd")
    Call<AreasResponse> getAreas(@Header("Token") String token,@Query("contactId") String contactId,@Query("suburb") String suburb,
                                 @Query("geoCoordinates") String geoCoordinates, @Query("address") String address,
                                 @Query("cityId") String cityId);

//    @GET("api/bindvsfilterdd?contactId=1&suburb=kurla&geoCoordinates=19.0715873,72.8654386&address=test&cityId=1")
//    Call<AreasResponse> getAreas(@Header("Token") String token);


}
