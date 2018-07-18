package com.tag.tai.tag.Services.Responses.Login;

/**
 * Created by Jam on 08-03-2018.
 */

public class LoginResponse {

    String action;
    String message;
    String authToken;
    LoginData loginDetails;


    public LoginResponse(String action, String message, String authToken, LoginData loginDetails) {
        this.action = action;
        this.message = message;
        this.authToken = authToken;
        this.loginDetails = loginDetails;
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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public LoginData getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(LoginData loginDetails) {
        this.loginDetails = loginDetails;
    }
}
