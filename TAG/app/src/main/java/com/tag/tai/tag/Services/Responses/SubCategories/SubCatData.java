package com.tag.tai.tag.Services.Responses.SubCategories;

/**
 * Created by Jam on 18-03-2018.
 */

public class SubCatData {

    String subCatId;
    String catId;
    String name;
    String microCategoryToolTip;
    String commentsToolTip;
    String isLocal;
    int subCatCount;

    public SubCatData(String subCatId, String catId, String name, String microCategoryToolTip, String commentsToolTip, String isLocal) {
        this.subCatId = subCatId;
        this.catId = catId;
        this.name = name;
        this.microCategoryToolTip = microCategoryToolTip;
        this.commentsToolTip = commentsToolTip;
        this.isLocal = isLocal;
    }

    public String getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(String isLocal) {
        this.isLocal = isLocal;
    }

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMicroCategoryToolTip() {
        return microCategoryToolTip;
    }

    public void setMicroCategoryToolTip(String microCategoryToolTip) {
        this.microCategoryToolTip = microCategoryToolTip;
    }

    public String getCommentsToolTip() {
        return commentsToolTip;
    }

    public void setCommentsToolTip(String commentsToolTip) {
        this.commentsToolTip = commentsToolTip;
    }

    public int getSubCatCount() {
        return subCatCount;
    }

    public void setSubCatCount(int subCatCount) {
        this.subCatCount = subCatCount;
    }
}
