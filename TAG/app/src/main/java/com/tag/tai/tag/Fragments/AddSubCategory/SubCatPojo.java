package com.tag.tai.tag.Fragments.AddSubCategory;

/**
 * Created by Jam on 20-03-2018.
 */

public class SubCatPojo {

    String microId;
    String categoryName;
    String categoryGroup;
    String categoryType;

    public SubCatPojo(String microId, String categoryName, String categoryGroup, String categoryType) {
        this.microId = microId;
        this.categoryName = categoryName;
        this.categoryGroup = categoryGroup;
        this.categoryType = categoryType;
    }

    public String getMicroId() {
        return microId;
    }

    public void setMicroId(String microId) {
        this.microId = microId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryGroup() {
        return categoryGroup;
    }

    public void setCategoryGroup(String categoryGroup) {
        this.categoryGroup = categoryGroup;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
