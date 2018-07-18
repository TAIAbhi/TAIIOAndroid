package com.tag.tai.tag.Fragments.FindSugesstions;

import com.tag.tai.tag.Services.Responses.GetSuggestions.SuggestionData;

import java.util.ArrayList;

/**
 * Created by Jam on 18-03-2018.
 */

public class EachSuggestionCategory {

    String categoryid;
    String subcategoryid;
    String microcatid;
    String categoryname;
    String subcategoryname;
    String microcatname;
    ArrayList<SuggestionData> suggestions;

    public EachSuggestionCategory(String categoryid, String subcategoryid, String microcatid, String categoryname, String subcategoryname, String microcatname, ArrayList<SuggestionData> suggestions) {
        this.categoryid = categoryid;
        this.subcategoryid = subcategoryid;
        this.microcatid = microcatid;
        this.categoryname = categoryname;
        this.subcategoryname = subcategoryname;
        this.microcatname = microcatname;
        this.suggestions = suggestions;
    }

    public String getMicrocatid() {
        return microcatid;
    }

    public void setMicrocatid(String microcatid) {
        this.microcatid = microcatid;
    }

    public String getMicrocatname() {
        return microcatname;
    }

    public void setMicrocatname(String microcatname) {
        this.microcatname = microcatname;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getSubcategoryid() {
        return subcategoryid;
    }

    public void setSubcategoryid(String subcategoryid) {
        this.subcategoryid = subcategoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getSubcategoryname() {
        return subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname) {
        this.subcategoryname = subcategoryname;
    }

    public ArrayList<SuggestionData> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(ArrayList<SuggestionData> suggestions) {
        this.suggestions = suggestions;
    }
}
