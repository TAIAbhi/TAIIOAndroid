package com.tag.tai.tag.Services.Requests.AddContact;

public class AddContactRequest {

    private String contactName;
    private String contactNumber;
    private String sourceId;
    private String location1;
    private String location2;
    private String location3;
    private String contactLevelUnderstanding;
    private String notification;
    private String isContactDetailsAdded;
    private String comments;
    private String platform;


    public AddContactRequest(String contactName, String contactNumber, String sourceId, String location1, String location2, String location3, String contactLevelUnderstanding, String notification, String isContactDetailsAdded, String comments, String platform) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.sourceId = sourceId;
        this.location1 = location1;
        this.location2 = location2;
        this.location3 = location3;
        this.contactLevelUnderstanding = contactLevelUnderstanding;
        this.notification = notification;
        this.isContactDetailsAdded = isContactDetailsAdded;
        this.comments = comments;
        this.platform = platform;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
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

    public String getContactLevelUnderstanding() {
        return contactLevelUnderstanding;
    }

    public void setContactLevelUnderstanding(String contactLevelUnderstanding) {
        this.contactLevelUnderstanding = contactLevelUnderstanding;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
