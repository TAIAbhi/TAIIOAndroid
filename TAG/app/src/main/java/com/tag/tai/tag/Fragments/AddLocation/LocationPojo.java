package com.tag.tai.tag.Fragments.AddLocation;

/**
 * Created by Jam on 20-03-2018.
 */

public class LocationPojo {

    String suburb;
    String locationname;
    String locationId;
    String locationtype;

    public LocationPojo(String suburb, String locationname, String locationId, String locationtype) {
        this.suburb = suburb;
        this.locationname = locationname;
        this.locationId = locationId;
        this.locationtype = locationtype;
    }

    public String getLocationtype() {
        return locationtype;
    }

    public void setLocationtype(String locationtype) {
        this.locationtype = locationtype;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
