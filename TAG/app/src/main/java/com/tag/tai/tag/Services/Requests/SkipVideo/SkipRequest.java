package com.tag.tai.tag.Services.Requests.SkipVideo;

/**
 * Created by Jam on 25-04-2018.
 */

public class SkipRequest {

    String contactId;
    String skipVideo;

    public SkipRequest(String contactId, String skipVideo) {
        this.contactId = contactId;
        this.skipVideo = skipVideo;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getSkipVideo() {
        return skipVideo;
    }

    public void setSkipVideo(String skipVideo) {
        this.skipVideo = skipVideo;
    }
}
