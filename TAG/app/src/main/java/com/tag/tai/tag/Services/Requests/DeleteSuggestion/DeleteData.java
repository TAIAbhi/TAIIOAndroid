package com.tag.tai.tag.Services.Requests.DeleteSuggestion;

public class DeleteData {

    String sugId;
    String reasonForChange;

    public DeleteData(String sugId, String reasonForChange) {
        this.sugId = sugId;
        this.reasonForChange = reasonForChange;
    }

    public String getSugId() {
        return sugId;
    }

    public void setSugId(String sugId) {
        this.sugId = sugId;
    }

    public String getReasonForChange() {
        return reasonForChange;
    }

    public void setReasonForChange(String reasonForChange) {
        this.reasonForChange = reasonForChange;
    }
}
