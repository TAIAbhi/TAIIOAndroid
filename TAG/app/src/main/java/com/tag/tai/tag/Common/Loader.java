package com.tag.tai.tag.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import static com.tag.tai.tag.Services.RetroClient.TAG;

/**
 * Created by Jam on 07-04-2018.
 */

public class Loader {

    ProgressDialog progress;
    LoaderControl control;

    public Loader(Context context,LoaderControl loaderControl) {
        this.progress = new ProgressDialog(context);

        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        this.control = loaderControl;
    }

    public void showLoader(String message) {
        progress.setMessage(message);
        //progress.show();
        //Log.d(TAG, "showLoader: " + message);

        control.showLoader();
    }

    public void showLoader(){
        //progress.show();
        control.showLoader();
    }

    public void hideLoader(){
        //progress.hide();
        control.hideLoader();
    }
}
