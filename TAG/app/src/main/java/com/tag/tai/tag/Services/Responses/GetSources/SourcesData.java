package com.tag.tai.tag.Services.Responses.GetSources;

/**
 * Created by Jam on 11-04-2018.
 */

public class SourcesData {

    String source;
    String sourceId;
    String contactNumber;


    public SourcesData(String source, String sourceId, String contactNumber) {
        this.source = source;
        this.sourceId = sourceId;
        this.contactNumber = contactNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}

