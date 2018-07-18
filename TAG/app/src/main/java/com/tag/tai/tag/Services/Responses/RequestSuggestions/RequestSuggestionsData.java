package com.tag.tai.tag.Services.Responses.RequestSuggestions;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestSuggestionsData implements Parcelable {

    private String contactNumber;
    private String category;
    private String categoryId;
    private String subCategory;
    private String subCategoryId;
    private String microcategory;
    private String microcategoryId;
    private String location;
    private String comments;
    private String contactName;
    private String contactId;
    private String sourceId;
    private String sourceName;
    private String uid;
    private String cityId;
    private String cityName;
    private String addedWhen;

    public RequestSuggestionsData(String contactNumber, String category, String categoryId, String subCategory, String subCategoryId, String microcategory, String microcategoryId, String location, String comments, String contactName, String contactId, String sourceId, String sourceName, String uid, String cityId, String cityName, String addedWhen) {
        this.contactNumber = contactNumber;
        this.category = category;
        this.categoryId = categoryId;
        this.subCategory = subCategory;
        this.subCategoryId = subCategoryId;
        this.microcategory = microcategory;
        this.microcategoryId = microcategoryId;
        this.location = location;
        this.comments = comments;
        this.contactName = contactName;
        this.contactId = contactId;
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.uid = uid;
        this.cityId = cityId;
        this.cityName = cityName;
        this.addedWhen = addedWhen;
    }



    public String getAddedWhen() {
        return addedWhen;
    }

    public void setAddedWhen(String addedWhen) {
        this.addedWhen = addedWhen;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getMicrocategory() {
        return microcategory;
    }

    public void setMicrocategory(String microcategory) {
        this.microcategory = microcategory;
    }

    public String getMicrocategoryId() {
        return microcategoryId;
    }

    public void setMicrocategoryId(String microcategoryId) {
        this.microcategoryId = microcategoryId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(contactNumber);
        dest.writeString(category);
        dest.writeString(categoryId);
        dest.writeString(subCategory);
        dest.writeString(subCategoryId);
        dest.writeString(microcategory);
        dest.writeString(microcategoryId);
        dest.writeString(location);
        dest.writeString(comments);
        dest.writeString(contactName);
        dest.writeString(contactId);
        dest.writeString(sourceId);
        dest.writeString(sourceName);
        dest.writeString(uid);
        dest.writeString(cityId);
        dest.writeString(cityName);
        dest.writeString(addedWhen);

    }

    protected RequestSuggestionsData(Parcel in) {
        contactNumber = in.readString();
        category = in.readString();
        categoryId = in.readString();
        subCategory = in.readString();
        subCategoryId = in.readString();
        microcategory = in.readString();
        microcategoryId = in.readString();
        location = in.readString();
        comments = in.readString();
        contactName = in.readString();
        contactId = in.readString();
        sourceId = in.readString();
        sourceName = in.readString();
        uid = in.readString();
        cityId = in.readString();
        cityName = in.readString();
        addedWhen = in.readString();
    }

    public static final Creator<RequestSuggestionsData> CREATOR = new Creator<RequestSuggestionsData>() {
        @Override
        public RequestSuggestionsData createFromParcel(Parcel in) {
            return new RequestSuggestionsData(in);
        }

        @Override
        public RequestSuggestionsData[] newArray(int size) {
            return new RequestSuggestionsData[size];
        }
    };
}
