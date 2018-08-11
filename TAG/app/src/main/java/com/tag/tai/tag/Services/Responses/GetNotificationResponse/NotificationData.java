package com.tag.tai.tag.Services.Responses.GetNotificationResponse;

public class NotificationData {
    int redirectToType;
    String target;
    String LocationId;
    String CatId;
    String SubCatId;
    String MCId;
    String SubCategoryName;
    String CategoryName;
    String LocationName;
    String MCName;
    String addedBy;
    String addedWhen;
    String redirectTo;
    int suggestionId;

    public NotificationData(String target, String locationId, String catId, String subCatId, String MCId, String subCategoryName, String categoryName, String locationName, String MCName, String addedBy, String addedWhen) {
        this.target = target;
        LocationId = locationId;
        CatId = catId;
        SubCatId = subCatId;
        this.MCId = MCId;
        SubCategoryName = subCategoryName;
        CategoryName = categoryName;
        LocationName = locationName;
        this.MCName = MCName;
        this.addedBy = addedBy;
        this.addedWhen = addedWhen;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedWhen() {
        return addedWhen;
    }

    public void setAddedWhen(String addedWhen) {
        this.addedWhen = addedWhen;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSubCategoryName() {
        return SubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        SubCategoryName = subCategoryName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getMCName() {
        return MCName;
    }

    public void setMCName(String MCName) {
        this.MCName = MCName;
    }

    public String getLocationId() {
        return LocationId;
    }

    public void setLocationId(String locationId) {
        LocationId = locationId;
    }

    public String getCatId() {
        return CatId;
    }

    public void setCatId(String catId) {
        CatId = catId;
    }

    public String getSubCatId() {
        return SubCatId;
    }

    public void setSubCatId(String subCatId) {
        SubCatId = subCatId;
    }

    public String getMCId() {
        return MCId;
    }

    public void setMCId(String MCId) {
        this.MCId = MCId;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    public int getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(int suggestionId) {
        this.suggestionId = suggestionId;
    }

    public int getRedirectToType() {
        return redirectToType;
    }

    public void setRedirectToType(int redirectToType) {
        this.redirectToType = redirectToType;
    }
}
