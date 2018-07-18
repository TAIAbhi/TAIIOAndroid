package com.tag.tai.tag.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jam on 08-03-2018.
 */

public class RetroClient {

    public static final String BASE_URL = "http://stringsconnected.com/";
    //public static final String BASE_URL = "http://devapitai.us-east-1.elasticbeanstalk.com/";
    public static final String TAG = "12345";
    public static final String CONNETION_ERROR = "There seems to be a connection error.";

    private static Retrofit retroClient = null;

    public static Retrofit getClient(){

        if(retroClient == null){
            retroClient = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retroClient;
    }

}
