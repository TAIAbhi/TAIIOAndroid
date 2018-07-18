package com.tag.tai.tag.Services.Responses.CategoryCountResponse;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private ArrayList<SectionCountDatum> SectionCountData;
    private ArrayList<CatergoryObject> CategoryCountData;

    public Data(ArrayList<SectionCountDatum> sectionCountData, ArrayList<CatergoryObject> categoryCountData) {
        SectionCountData = sectionCountData;
        CategoryCountData = categoryCountData;
    }

    public ArrayList<SectionCountDatum> getSectionCountData() {
        return SectionCountData;
    }

    public void setSectionCountData(ArrayList<SectionCountDatum> sectionCountData) {
        SectionCountData = sectionCountData;
    }

    public ArrayList<CatergoryObject> getCategoryCountData() {
        return CategoryCountData;
    }

    public void setCategoryCountData(ArrayList<CatergoryObject> categoryCountData) {
        CategoryCountData = categoryCountData;
    }
}