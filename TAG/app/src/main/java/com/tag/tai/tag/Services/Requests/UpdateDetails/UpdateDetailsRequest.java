package com.tag.tai.tag.Services.Requests.UpdateDetails;

/**
 * Created by Jam on 22-03-2018.
 */

public class UpdateDetailsRequest {

    String contactId;
    String contactName;
    String contactNumber;
    String location1;
    String location2;
    String location3;
    String contactLevelUnderstating;
    String notification;
    String isContactDetailsAdded;
    String comments;
    String platform;
    String allowProvideSuggestion;

    public UpdateDetailsRequest(String contactId, String contactName, String contactNumber, String location1, String location2, String location3, String contactLevelUnderstating, String notification, String isContactDetailsAdded, String comments, String platform, String allowProvideSuggestion) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.location1 = location1;
        this.location2 = location2;
        this.location3 = location3;
        this.contactLevelUnderstating = contactLevelUnderstating;
        this.notification = notification;
        this.isContactDetailsAdded = isContactDetailsAdded;
        this.comments = comments;
        this.platform = platform;
        this.allowProvideSuggestion = allowProvideSuggestion;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAllowProvideSuggestion() {
        return allowProvideSuggestion;
    }

    public void setAllowProvideSuggestion(String allowProvideSuggestion) {
        this.allowProvideSuggestion = allowProvideSuggestion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getLocation1() {
        return location1;
    }

    public void setLocation1(String location1) {
        this.location1 = location1;
    }

    public String getLocation2() {
        return location2;
    }

    public void setLocation2(String location2) {
        this.location2 = location2;
    }

    public String getLocation3() {
        return location3;
    }

    public void setLocation3(String location3) {
        this.location3 = location3;
    }

    public String getContactLevelUnderstating() {
        return contactLevelUnderstating;
    }

    public void setContactLevelUnderstating(String contactLevelUnderstating) {
        this.contactLevelUnderstating = contactLevelUnderstating;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getIsContactDetailsAdded() {
        return isContactDetailsAdded;
    }

    public void setIsContactDetailsAdded(String isContactDetailsAdded) {
        this.isContactDetailsAdded = isContactDetailsAdded;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
