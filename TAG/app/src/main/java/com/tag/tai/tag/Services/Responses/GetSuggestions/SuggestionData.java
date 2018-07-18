package com.tag.tai.tag.Services.Responses.GetSuggestions;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jam on 18-03-2018.
 */

public class SuggestionData implements Parcelable{

    String contactNumber;
    String businessName;
    String businessContact;
    String category;
    String subCategory;
    String microcategory;
    String sourceName;
    String location;
    String citiLevelBusiness;
    String comments;
    String contactName;
    String suggestionId;
    String sourceId;
    String contactId;
    String categoryId;
    String subCategoryId;
    String isAChain;
    String microcategoryId;
    String tagCount;
    String addedWhen;
    String cityId;
    String cityName;

    String lastAddedWhen;
    String contactComment;
    String lastAddedBy;
    String showMaps;
    String usedTagSuggetion;
    String vendorIsVerified;


    public String getVendorIsVerified() {
        return vendorIsVerified;
    }

    public void setVendorIsVerified(String vendorIsVerified) {
        this.vendorIsVerified = vendorIsVerified;
    }

    public String getUsedTagSuggetion() {
        return usedTagSuggetion;
    }

    public void setUsedTagSuggetion(String usedTagSuggetion) {
        this.usedTagSuggetion = usedTagSuggetion;
    }

    public String getShowMaps() {
        return showMaps;
    }

    public void setShowMaps(String showMaps) {
        this.showMaps = showMaps;
    }

    public String getLastAddedBy() {
        return lastAddedBy;
    }

    public void setLastAddedBy(String lastAddedBy) {
        this.lastAddedBy = lastAddedBy;
    }

    public String getContactComment() {
        return contactComment;
    }

    public void setContactComment(String contactComment) {
        this.contactComment = contactComment;
    }

    public String getLastAddedWhen() {
        return lastAddedWhen;
    }

    public void setLastAddedWhen(String lastAddedWhen) {
        this.lastAddedWhen = lastAddedWhen;
    }

    public String getAddedWhen() {
        return addedWhen;
    }

    public void setAddedWhen(String addedWhen) {
        this.addedWhen = addedWhen;
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

    public String getTagCount() {
        return tagCount;
    }

    public void setTagCount(String tagCount) {
        this.tagCount = tagCount;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessContact() {
        return businessContact;
    }

    public void setBusinessContact(String businessContact) {
        this.businessContact = businessContact;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getMicrocategory() {
        return microcategory;
    }

    public void setMicrocategory(String microcategory) {
        this.microcategory = microcategory;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCitiLevelBusiness() {
        return citiLevelBusiness;
    }

    public void setCitiLevelBusiness(String citiLevelBusiness) {
        this.citiLevelBusiness = citiLevelBusiness;
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

    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getIsAChain() {
        return isAChain;
    }

    public void setIsAChain(String isAChain) {
        this.isAChain = isAChain;
    }

    public String getMicrocategoryId() {
        return microcategoryId;
    }

    public void setMicrocategoryId(String microcategoryId) {
        this.microcategoryId = microcategoryId;
    }

    public static final Parcelable.Creator<SuggestionData> CREATOR = new Parcelable.Creator<SuggestionData>(){

        @Override
        public SuggestionData createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public SuggestionData[] newArray(int size) {
            return new SuggestionData[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(contactNumber);
        dest.writeString(businessName);
        dest.writeString(businessContact);
        dest.writeString(category);
        dest.writeString(subCategory);
        dest.writeString(microcategory);
        dest.writeString(sourceName);
        dest.writeString(location);
        dest.writeString(citiLevelBusiness);
        dest.writeString(comments);
        dest.writeString(contactName);
        dest.writeString(suggestionId);
        dest.writeString(sourceId);
        dest.writeString(contactId);
        dest.writeString(categoryId);
        dest.writeString(subCategoryId);
        dest.writeString(isAChain);
        dest.writeString(microcategoryId);
        dest.writeString(tagCount);
        dest.writeString(cityId);
        dest.writeString(cityName);
        dest.writeString(lastAddedWhen);
        dest.writeString(contactComment);
        dest.writeString(lastAddedBy);
    }
}
