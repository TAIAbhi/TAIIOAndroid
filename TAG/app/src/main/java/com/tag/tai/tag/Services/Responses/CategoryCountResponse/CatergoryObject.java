package com.tag.tai.tag.Services.Responses.CategoryCountResponse;

public class CatergoryObject {

    int catId;
    String name;
    int subCatId;
    String categoryName;
    int suggCount;

    public CatergoryObject(int catId, String name, int subCatId, String categoryName, int suggCount) {
        this.catId = catId;
        this.name = name;
        this.subCatId = subCatId;
        this.categoryName = categoryName;
        this.suggCount = suggCount;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(int subCatId) {
        this.subCatId = subCatId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSuggCount() {
        return suggCount;
    }

    public void setSuggCount(int suggCount) {
        this.suggCount = suggCount;
    }
}
