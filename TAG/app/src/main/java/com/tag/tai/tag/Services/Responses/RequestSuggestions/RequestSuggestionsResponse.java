package com.tag.tai.tag.Services.Responses.RequestSuggestions;

import java.util.ArrayList;

public class RequestSuggestionsResponse {

    private String action;
    private String message;
    private String pageInfo;
    private ArrayList<RequestSuggestionsData> data;

    public RequestSuggestionsResponse(String action, String message, String pageInfo, ArrayList<RequestSuggestionsData> data) {
        this.action = action;
        this.message = message;
        this.pageInfo = pageInfo;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(String pageInfo) {
        this.pageInfo = pageInfo;
    }

    public ArrayList<RequestSuggestionsData> getData() {
        return data;
    }

    public void setData(ArrayList<RequestSuggestionsData> data) {
        this.data = data;
    }
}
