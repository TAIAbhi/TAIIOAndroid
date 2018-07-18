package com.tag.tai.tag.Common;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

public class ProcessError {

    public static void processError(Context context, String errorBody){


        Map<String,String> m = new Gson().fromJson(errorBody,Map.class);

        if (context!=null & m!=null)
        Toast.makeText(context, "" + m.get("message"), Toast.LENGTH_SHORT).show();

    };
}
