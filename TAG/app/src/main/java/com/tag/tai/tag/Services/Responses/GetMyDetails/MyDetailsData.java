package com.tag.tai.tag.Services.Responses.GetMyDetails;

/**
 * Created by Jam on 18-03-2018.
 */

public class MyDetailsData {

    String sourceId;
    String source;
    String contactId;
    String contact;
    String location1;
    String location2;
    String location3;
    String contactNumber;
    String contactComments;
    String contactLevelUnderstanding;
    String notification;
    String isContactDetailsAdded;
    boolean allowProvideSuggestion;


    public MyDetailsData(String sourceId, String source, String contactId, String contact, String location1, String location2, String location3, String contactNumber, String contactComments, String contactLevelUnderstanding, String notification, String isContactDetailsAdded, boolean allowProvideSuggestion) {
        this.sourceId = sourceId;
        this.source = source;
        this.contactId = contactId;
        this.contact = contact;
        this.location1 = location1;
        this.location2 = location2;
        this.location3 = location3;
        this.contactNumber = contactNumber;
        this.contactComments = contactComments;
        this.contactLevelUnderstanding = contactLevelUnderstanding;
        this.notification = notification;
        this.isContactDetailsAdded = isContactDetailsAdded;
        this.allowProvideSuggestion = allowProvideSuggestion;
    }

    public boolean isAllowProvideSuggestion() {
        return allowProvideSuggestion;
    }

    public void setAllowProvideSuggestion(boolean allowProvideSuggestion) {
        this.allowProvideSuggestion = allowProvideSuggestion;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactComments() {
        return contactComments;
    }

    public void setContactComments(String contactComments) {
        this.contactComments = contactComments;
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
}
