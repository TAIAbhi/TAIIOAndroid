package com.tag.tai.tag.Fragments.Rankings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.R;

/**
 * Created by Jam on 17-04-2018.
 */

public class RankingFragment extends Fragment {

    WebView web_rankings;

    public RankingFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_web_view, container, false);

        web_rankings = v.findViewById(R.id.web_feedback);
        web_rankings.setWebViewClient(new WebViewClient(){

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

        web_rankings.getSettings().setLoadsImagesAutomatically(true);
        web_rankings.getSettings().setJavaScriptEnabled(true);
        web_rankings.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //web_feedback.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfSHLrl5S8NFIauqbXgYvV3CC6ydbPshSilMx_SZJi0_Rz4dQ/viewform?c=0&w=1");
        web_rankings.loadUrl("http://tagaboutit.com/Login/Ranking?mobile="  + new SessionManager(getActivity()).getUserMobile());

        Log.d("12345", "onCreateView: " + "http://tagaboutit.com/Login/Ranking?mobile="  + new SessionManager(getActivity()).getUserMobile());

        return v;
    }
}
