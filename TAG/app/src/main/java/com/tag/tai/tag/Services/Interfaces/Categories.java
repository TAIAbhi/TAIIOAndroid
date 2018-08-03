package com.tag.tai.tag.Services.Interfaces;

import com.tag.tai.tag.Services.Responses.Categories.CategoryResponse;
import com.tag.tai.tag.Services.Responses.MicroCat.MicroCatResponse;
import com.tag.tai.tag.Services.Responses.SubCategories.SubCatResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Jam on 18-03-2018.
 */

public interface Categories {

    @GET("api/subcat")
    Call<SubCatResponse> getCategory(@Header("Token") String token, @Query("categoryId") String category);

    @GET("api/microcat")
    Call<MicroCatResponse> getSubCategory(@Header("Token") String token, @Query("subcategoryId") String category);

    @GET("api/categories")
    Call<CategoryResponse> getCategories(@Header("Token") String token, @Query("isRequest") String isRequestBool);
}
