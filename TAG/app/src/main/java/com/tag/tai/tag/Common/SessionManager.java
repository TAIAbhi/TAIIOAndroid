package com.tag.tai.tag.Common;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by Jam on 15-03-2018.
 */

public class SessionManager {



    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String PREF_NAME = "TAG";

    // All Shared Preferences Keys
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    public static final String USER_ID = "UserID";
    public static final String USER_MOBILE = "UserMobile";
    public static final String TOKEN = "UserToken";
    public static final String ROLE = "Role";
    public static final String SPLASHSCREENPLAYTIME = "splashlastplayed";
    public static final String CURRENTCITY = "currentcity";
    public static final String EXIT_TIME = "tag.exit.time";

    public static final String SOURCEIMAGE = "sourceimage";
    public static final String SOURCETEXT = "sourcetext";
    public static final String SOURCE_NAME = "sourcename";
    public static final String CONTACT_NAME = "contactname";
    private static final String LOGINID = "lastloginid";
    private static final String LAST_LOCATION = "lastlocation";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(PREF_NAME,0);
        editor = sharedPreferences.edit();
    }

    public void setUserLoggedIn(String contactName,String userid,String mobile,String role,String sourceImage,String sourceText,String sourcename){
        editor.putString(CONTACT_NAME, contactName);
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_ID, userid);
        editor.putString(USER_MOBILE, mobile);
        editor.putString(ROLE,role);
        editor.putString(SOURCEIMAGE,sourceImage);
        editor.putString(SOURCETEXT,sourceText);
        editor.putString(SOURCE_NAME,sourcename);
        editor.commit();
    }

    public boolean IsLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }

    public void Logout(){
        editor.putBoolean(IS_LOGGED_IN,false);
        editor.commit();
    }

    public String getUserID(){
        return sharedPreferences.getString(USER_ID,null);
    }

    public String getUserMobile(){
        return sharedPreferences.getString(USER_MOBILE,null);
    }

    public String getRole(){
        return sharedPreferences.getString(ROLE,null);
    }

    public String getUserIntro(){
        return sharedPreferences.getString(SOURCETEXT,null);
    }
    public String getUserimage(){
        return sharedPreferences.getString(SOURCEIMAGE,null);
    }
    public String getContactName(){
        return sharedPreferences.getString(CONTACT_NAME,null);
    }
    public String getSourceName(){
        return sharedPreferences.getString(SOURCE_NAME,null);
    }

    public void setToken(String token){
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public String getToken(){
        return sharedPreferences.getString(TOKEN,null);
    }


    public void setlastsplashsreentime(long time){
        editor.putLong(SPLASHSCREENPLAYTIME, time);
        editor.commit();
    }

    public long getlastsplashsreentime(){
        return sharedPreferences.getLong(SPLASHSCREENPLAYTIME,-1);
    }

    public void setcurrentcity(int city){
        editor.putInt(CURRENTCITY, city);
        editor.commit();
    }


    public int getcurrentcity(){
        return sharedPreferences.getInt(CURRENTCITY,1);
    }

    public void setlastlogincreds(String loginid){
        editor.putString(LOGINID, loginid);
        editor.commit();
    }

    public String getlastlogincreds(){
        return sharedPreferences.getString(LOGINID,"");
    }

    public void setlastknownlocation(String location){
        editor.putString(LAST_LOCATION, location);
        editor.commit();
    }

    public String getlastknownlocation(){
        return sharedPreferences.getString(LAST_LOCATION,"");
    }

    //exit time
    public void saveExitTime() {
        editor.putLong(EXIT_TIME, new Date().getTime());
        editor.commit();
    }

    public boolean isLocationTimeExpired() {
        long lastExit = sharedPreferences.getLong(EXIT_TIME, 0l);
        long currentTime = new Date().getTime();
        return (currentTime - lastExit) >= 10000;
    }
}
