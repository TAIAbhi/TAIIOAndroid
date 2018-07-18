package com.tag.tai.tag.Services.Responses.GetLocations;

/**
 * Created by Jam on 20-03-2018.
 */

public class LocationsData {

    String locationId;
    String city;
    String area;
    String suburb;
    String locationName;
    String locSuburb;

    public LocationsData(String locationId, String city, String area, String suburb, String locationName, String locSuburb) {
        this.locationId = locationId;
        this.city = city;
        this.area = area;
        this.suburb = suburb;
        this.locationName = locationName;
        this.locSuburb = locSuburb;
    }

    public String getLocSuburb() {
        return locSuburb;
    }

    public void setLocSuburb(String locSuburb) {
        this.locSuburb = locSuburb;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
}
