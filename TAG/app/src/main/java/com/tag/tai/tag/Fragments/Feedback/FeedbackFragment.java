package com.tag.tai.tag.Fragments.Feedback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.R;

/**
 * Created by Jam on 01-04-2018.
 */

public class FeedbackFragment extends Fragment {

    WebView web_feedback;

    public FeedbackFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback,container,false);

        web_feedback = v.findViewById(R.id.web_feedback);
        web_feedback.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                Toast.makeText(getActivity(), "Error : " + description, Toast.LENGTH_SHORT).show();
            }
        });

        web_feedback.getSettings().setLoadsImagesAutomatically(true);
        web_feedback.getSettings().setJavaScriptEnabled(true);
        web_feedback.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //web_feedback.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfSHLrl5S8NFIauqbXgYvV3CC6ydbPshSilMx_SZJi0_Rz4dQ/viewform?c=0&w=1");
        web_feedback.loadUrl("http://Tagaboutit.com/Login/Feedback?mobile="  + new SessionManager(getActivity()).getUserMobile());

        return v;
    }
}
