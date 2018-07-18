package com.tag.tai.tag.Services.Responses.HelpDetails;

import java.util.ArrayList;

/**
 * Created by Jam on 13-04-2018.
 */

public class HelpData {

    String moduleName;
    ArrayList<String> uRLorText;

    public HelpData(String moduleName, ArrayList<String> uRLorText) {
        this.moduleName = moduleName;
        this.uRLorText = uRLorText;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public ArrayList<String> getuRLorText() {
        return uRLorText;
    }

    public void setuRLorText(ArrayList<String> uRLorText) {
        this.uRLorText = uRLorText;
    }
}
