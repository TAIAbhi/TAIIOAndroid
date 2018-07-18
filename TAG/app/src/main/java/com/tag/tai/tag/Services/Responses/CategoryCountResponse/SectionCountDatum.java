package com.tag.tai.tag.Services.Responses.CategoryCountResponse;

public class SectionCountDatum {

    private Integer catId;
    private String name;
    private Integer suggCount;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSuggCount() {
        return suggCount;
    }

    public void setSuggCount(Integer suggCount) {
        this.suggCount = suggCount;
    }

}