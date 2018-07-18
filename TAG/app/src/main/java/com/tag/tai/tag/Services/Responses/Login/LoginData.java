package com.tag.tai.tag.Services.Responses.Login;

/**
 * Created by Jam on 18-03-2018.
 */

public class LoginData {

    String sourceId;
    String contactId;
    String sourceName;
    String mobile;
    String contactName;
    String role;
    String skipVideo;
    String showVideo;
    String sourceType;
    String videoUrl;
    String sourceImage;
    String sourceTypeText;

    public LoginData(String sourceId, String contactId, String sourceName, String mobile, String contactName, String role, String skipVideo, String showVideo, String sourceType, String videoUrl, String sourceImage, String sourceTypeText) {
        this.sourceId = sourceId;
        this.contactId = contactId;
        this.sourceName = sourceName;
        this.mobile = mobile;
        this.contactName = contactName;
        this.role = role;
        this.skipVideo = skipVideo;
        this.showVideo = showVideo;
        this.sourceType = sourceType;
        this.videoUrl = videoUrl;
        this.sourceImage = sourceImage;
        this.sourceTypeText = sourceTypeText;
    }

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public String getSourceTypeText() {
        return sourceTypeText;
    }

    public void setSourceTypeText(String sourceTypeText) {
        this.sourceTypeText = sourceTypeText;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSkipVideo() {
        return skipVideo;
    }

    public void setSkipVideo(String skipVideo) {
        this.skipVideo = skipVideo;
    }

    public String getShowVideo() {
        return showVideo;
    }

    public void setShowVideo(String showVideo) {
        this.showVideo = showVideo;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
