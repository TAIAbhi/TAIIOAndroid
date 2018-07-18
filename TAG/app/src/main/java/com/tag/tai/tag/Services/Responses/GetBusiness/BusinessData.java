package com.tag.tai.tag.Services.Responses.GetBusiness;

/**
 * Created by Jam on 11-04-2018.
 */

public class BusinessData {

    String businessName;
    String isLocal;
    String isachain;
    String location;
    String businessContact;

    public BusinessData(String businessName, String isLocal, String isachain, String location, String businessContact) {
        this.businessName = businessName;
        this.isLocal = isLocal;
        this.isachain = isachain;
        this.location = location;
        this.businessContact = businessContact;
    }

    public String getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(String isLocal) {
        this.isLocal = isLocal;
    }

    public String getIsachain() {
        return isachain;
    }

    public void setIsachain(String isachain) {
        this.isachain = isachain;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBusinessContact() {
        return businessContact;
    }

    public void setBusinessContact(String businessContact) {
        this.businessContact = businessContact;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
