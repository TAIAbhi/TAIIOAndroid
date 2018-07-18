package com.tag.tai.tag.Services.Responses.GetContacts;

/**
 * Created by Jam on 11-04-2018.
 */

public class ContactData {

    String contactId;
    String contact;
    String contactNumber;

    public ContactData(String contactId, String contact, String contactNumber) {
        this.contactId = contactId;
        this.contact = contact;
        this.contactNumber = contactNumber;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
