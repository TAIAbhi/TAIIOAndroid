package com.tag.tai.tag.Services.Responses.GetAreas;

import android.os.Parcel;
import android.os.Parcelable;

public class AreaData implements Parcelable {

    int sequienceId;
    String ddValue;
    String ddText;
    String filterType;
    boolean isSelected;
    String cityId;

    public AreaData(int sequienceId, String ddValue, String ddText, String filterType, boolean isSelected) {
        this.sequienceId = sequienceId;
        this.ddValue = ddValue;
        this.ddText = ddText;
        this.filterType = filterType;
        this.isSelected = isSelected;
    }

    public int getSequienceId() {
        return sequienceId;
    }

    public void setSequienceId(int sequienceId) {
        this.sequienceId = sequienceId;
    }

    public String getDdValue() {
        return ddValue;
    }

    public void setDdValue(String ddValue) {
        this.ddValue = ddValue;
    }

    public String getDdText() {
        return ddText;
    }

    public void setDdText(String ddText) {
        this.ddText = ddText;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCityId(String cityId){
        this.cityId = cityId;
    }

    public String getCityId(){
        return cityId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
