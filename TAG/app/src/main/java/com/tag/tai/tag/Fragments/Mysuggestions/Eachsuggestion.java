package com.tag.tai.tag.Fragments.Mysuggestions;

/**
 * Created by Jam on 18-03-2018.
 */

public class Eachsuggestion {

    String suggestionid;
    String businessname;
    String businesslocation;
    String businesscontact;
    String comments;
    String referee;

    public Eachsuggestion(String suggestionid, String businessname, String businesslocation, String businesscontact, String comments, String referee) {
        this.suggestionid = suggestionid;
        this.businessname = businessname;
        this.businesslocation = businesslocation;
        this.businesscontact = businesscontact;
        this.comments = comments;
        this.referee = referee;
    }

    public String getSuggestionid() {
        return suggestionid;
    }

    public void setSuggestionid(String suggestionid) {
        this.suggestionid = suggestionid;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getBusinesslocation() {
        return businesslocation;
    }

    public void setBusinesslocation(String businesslocation) {
        this.businesslocation = businesslocation;
    }

    public String getBusinesscontact() {
        return businesscontact;
    }

    public void setBusinesscontact(String businesscontact) {
        this.businesscontact = businesscontact;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }
}
