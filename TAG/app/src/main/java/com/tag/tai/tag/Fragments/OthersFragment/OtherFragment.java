package com.tag.tai.tag.Fragments.OthersFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.R;

public class OtherFragment extends Fragment {

    WebView web_others;
    SessionManager session;

    public OtherFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_other,container,false);

        session = new SessionManager(getActivity());

        web_others = v.findViewById(R.id.web_others);
        web_others.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                //Toast.makeText(getActivity(), "Error : " + description, Toast.LENGTH_SHORT).show();
            }
        });

        web_others.getSettings().setLoadsImagesAutomatically(true);
        web_others.getSettings().setJavaScriptEnabled(true);
        web_others.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //web_feedback.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfSHLrl5S8NFIauqbXgYvV3CC6ydbPshSilMx_SZJi0_Rz4dQ/viewform?c=0&w=1");
        web_others.loadUrl("http://www.tagaboutit.com/FAQ/FAQ.html?mobile="+ session.getUserMobile() +"&city=" + session.getcurrentcity());


        return v;
    }
}
