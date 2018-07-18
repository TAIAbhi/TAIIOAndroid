package com.tag.tai.tag.Services.Interfaces;

import com.tag.tai.tag.Services.Requests.AddSuggestion.SuggestionRequest;
import com.tag.tai.tag.Services.Requests.DeleteSuggestion.DeleteData;
import com.tag.tai.tag.Services.Requests.UpdateSuggestion.UpdateSuggestionRequest;
import com.tag.tai.tag.Services.Responses.AddSuggestion.AddSuggestionResponse;
import com.tag.tai.tag.Services.Responses.CategoryCountResponse.CategoryCountResponse;
import com.tag.tai.tag.Services.Responses.DeleteSuggestion.DeleteSuggestionResponse;
import com.tag.tai.tag.Services.Responses.GetSuggestions.SuggestionsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Jam on 18-03-2018.
 */

public interface Suggestions {

    @GET("api/getsuggestion")
    Call<SuggestionsResponse> getusersuggestions(@Header("Token") String token, @Query("contactId") String contactid);

    @GET("api/getsuggestion?getall=2")
    Call<SuggestionsResponse> getallsuggestions(@Header("Token") String token);

    @GET("api/getsuggestion?getall=2&pageSize=20")
    Call<SuggestionsResponse> getallsuggestionsbyfilter(@Header("Token") String token,
                                                        @Query("catId") String categoryId,
                                                        @Query("subCatId") String subCatId,
                                                        @Query("contactId") String contactId,
                                                        @Query("sourceId") String sourceId,
                                                        @Query("businessName") String businessName,
                                                        @Query("isLocal") String isLocal,
                                                        @Query("location") String location,
                                                        @Query("microName") String microName,
                                                        @Query("pageNumber") String pageNumber);


    @GET("api/getsuggestionwithcount?getall=2&pageSize=20&platform=2")
    Call<SuggestionsResponse> getallsuggestionsbyfilterandcount(@Header("Token") String token,
                                                        @Query("catId") String categoryId,
                                                        @Query("subCatId") String subCatId,
                                                        @Query("contactId") String contactId,
                                                        @Query("sourceId") String sourceId,
                                                        @Query("businessName") String businessName,
                                                        @Query("isLocal") String isLocal,
                                                        @Query("location") String location,
                                                        @Query("microName") String microName,
                                                        @Query("cityId") String cityId,
                                                        @Query("pageNumber") String pageNumber,
                                                        @Query("areaShortCode") String areaShortCode);

    //add suggestions
    @POST("api/suggestion")
    Call<AddSuggestionResponse> savesuggestion(@Header("Token") String token, @Body SuggestionRequest request);

    //update
    @PUT("api/suggestion")
    Call<AddSuggestionResponse> updatesuggestion(@Header("Token") String token, @Body UpdateSuggestionRequest request);

    //delete
    @HTTP(method = "DELETE", path = "api/deletesuggestion", hasBody = true)
    Call<DeleteSuggestionResponse> deleteSuggestion(@Header("Token") String token, @Body DeleteData request);


    //get suggestions count
    @GET("api/getsectioncategorywithcount?uniqueCount=false")
    Call<CategoryCountResponse> getCategoryCount(@Header("Token") String token, @Query("contactNumber") String contact,
                                                 @Query("suburb") String suburb, @Query("areaCode") String areacode,
                                                 @Query("cityId") String cityid);

}
