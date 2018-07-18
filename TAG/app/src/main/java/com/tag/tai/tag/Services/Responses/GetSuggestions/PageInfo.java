package com.tag.tai.tag.Services.Responses.GetSuggestions;

/**
 * Created by Jam on 11-04-2018.
 */

public class PageInfo {

    int pageNumber;
    int pageSize;
    int noOfRecord;

    public PageInfo(int pageNumber, int pageSize, int noOfRecord) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.noOfRecord = noOfRecord;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNoOfRecord() {
        return noOfRecord;
    }

    public void setNoOfRecord(int noOfRecord) {
        this.noOfRecord = noOfRecord;
    }
}
