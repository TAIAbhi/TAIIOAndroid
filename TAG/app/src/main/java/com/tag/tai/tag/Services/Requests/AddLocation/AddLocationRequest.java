package com.tag.tai.tag.Services.Requests.AddLocation;

public class AddLocationRequest {

    String suburb;
    String locationName;
    String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public AddLocationRequest(String suburb, String locationName, String city) {
        this.suburb = suburb;
        this.locationName = locationName;
        this.city = city;
    }
}
