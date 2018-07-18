package com.tag.tai.tag.Services.Responses.GetCities;

/**
 * Created by Jam on 01-05-2018.
 */

public class CityData {

    String cityId;
    String cityName;
    double minGeoLat;
    double maxGeoLat;
    double minGeoLong;
    double maxGeoLong;

    public CityData(String cityId, String cityName, double minGeoLat, double maxGeoLat, double minGeoLong, double maxGeoLong) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.minGeoLat = minGeoLat;
        this.maxGeoLat = maxGeoLat;
        this.minGeoLong = minGeoLong;
        this.maxGeoLong = maxGeoLong;
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

    public double getMinGeoLat() {
        return minGeoLat;
    }

    public void setMinGeoLat(double minGeoLat) {
        this.minGeoLat = minGeoLat;
    }

    public double getMaxGeoLat() {
        return maxGeoLat;
    }

    public void setMaxGeoLat(double maxGeoLat) {
        this.maxGeoLat = maxGeoLat;
    }

    public double getMinGeoLong() {
        return minGeoLong;
    }

    public void setMinGeoLong(double minGeoLong) {
        this.minGeoLong = minGeoLong;
    }

    public double getMaxGeoLong() {
        return maxGeoLong;
    }

    public void setMaxGeoLong(double maxGeoLong) {
        this.maxGeoLong = maxGeoLong;
    }
}
