package com.tag.tai.tag.Fragments.Mysuggestions;

import java.util.ArrayList;

/**
 * Created by Jam on 18-03-2018.
 */

public class EachSuggestionCategory {

    String categoryid;
    String subcategoryid;
    String categoryname;
    String subcategoryname;
    ArrayList<Eachsuggestion> suggestions;

    public EachSuggestionCategory(String categoryid, String subcategoryid, String categoryname, String subcategoryname, ArrayList<Eachsuggestion> suggestions) {
        this.categoryid = categoryid;
        this.subcategoryid = subcategoryid;
        this.categoryname = categoryname;
        this.subcategoryname = subcategoryname;
        this.suggestions = suggestions;
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

    public ArrayList<Eachsuggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(ArrayList<Eachsuggestion> suggestions) {
        this.suggestions = suggestions;
    }
}
