package com.tag.tai.tag.Services.Responses.MicroCat;

/**
 * Created by Jam on 20-03-2018.
 */

public class MicroCatData {

    String microId;
    String subCateId;
    String name;

    public MicroCatData(String microId, String subCateId, String name) {
        this.microId = microId;
        this.subCateId = subCateId;
        this.name = name;
    }

    public String getMicroId() {
        return microId;
    }

    public void setMicroId(String microId) {
        this.microId = microId;
    }

    public String getSubCateId() {
        return subCateId;
    }

    public void setSubCateId(String subCateId) {
        this.subCateId = subCateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
