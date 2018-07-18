package com.tag.tai.tag.Services.Responses.GetSuggestions;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jam on 18-03-2018.
 */

public class SuggestionsResponse {

    String action;
    String  message;
    ArrayList<SuggestionData> data;
    PageInfo pageInfo;

    public SuggestionsResponse(String action, String message, ArrayList<SuggestionData> data, PageInfo pageInfo) {
        this.action = action;
        this.message = message;
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SuggestionData> getData() {
        return data;
    }

    public void setData(ArrayList<SuggestionData> data) {
        this.data = data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
