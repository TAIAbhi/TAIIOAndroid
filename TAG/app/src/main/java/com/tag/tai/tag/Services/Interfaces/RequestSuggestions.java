package com.tag.tai.tag.Services.Interfaces;

import com.tag.tai.tag.Services.Responses.RequestSuggestions.RequestSuggestionsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestSuggestions {

    @GET("api/getrequestsuggestion")
    Call<RequestSuggestionsResponse> getRequestedSuggestions(@Header("Token") String token,
                                                             @Query("catId") String categoryId,
                                                             @Query("subCatId") String subCategoryId,
                                                             @Query("cityId") String cityId);

}
