package com.tag.tai.tag.Services.Requests.UpdateSuggestion;

/**
 * Created by Jam on 05-04-2018.
 */

public class UpdateSuggestionRequest {

    String sugId;
    String contactId;
    String catId;
    String subCategoryId;
    String microcategory;
    String businessName;
    String citiLevelBusiness;
    String businessContact;
    String location;
    String comments;
    String isAChain;
    String city;
    String platform;

    public UpdateSuggestionRequest(String sugId, String contactId, String catId, String subCategoryId, String microcategory, String businessName, String citiLevelBusiness, String businessContact, String location, String comments, String isAChain, String city, String platform) {
        this.sugId = sugId;
        this.contactId = contactId;
        this.catId = catId;
        this.subCategoryId = subCategoryId;
        this.microcategory = microcategory;
        this.businessName = businessName;
        this.citiLevelBusiness = citiLevelBusiness;
        this.businessContact = businessContact;
        this.location = location;
        this.comments = comments;
        this.isAChain = isAChain;
        this.city = city;
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSugId() {
        return sugId;
    }

    public void setSugId(String sugId) {
        this.sugId = sugId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCitiLevelBusiness() {
        return citiLevelBusiness;
    }

    public void setCitiLevelBusiness(String citiLevelBusiness) {
        this.citiLevelBusiness = citiLevelBusiness;
    }

    public String getBusinessContact() {
        return businessContact;
    }

    public void setBusinessContact(String businessContact) {
        this.businessContact = businessContact;
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

    public String getIsAChain() {
        return isAChain;
    }

    public void setIsAChain(String isAChain) {
        this.isAChain = isAChain;
    }
}
