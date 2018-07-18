package com.tag.tai.tag.Fragments.RequestSuggestions;

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

public class RequestSuggestionsFragment extends Fragment {

    WebView web_reqsuggestion;
    SessionManager session;

    String category;
    String subCategory;

    public RequestSuggestionsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reqsuggestions,container,false);

        category = getArguments().getString("selected_category");
        subCategory = getArguments().getString("selected_subcategory");
        session = new SessionManager(getActivity());

        web_reqsuggestion = v.findViewById(R.id.web_reqsuggestion);
        web_reqsuggestion.setWebViewClient(new WebViewClient(){

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

        web_reqsuggestion.getSettings().setLoadsImagesAutomatically(true);
        web_reqsuggestion.getSettings().setJavaScriptEnabled(true);
        web_reqsuggestion.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //web_feedback.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfSHLrl5S8NFIauqbXgYvV3CC6ydbPshSilMx_SZJi0_Rz4dQ/viewform?c=0&w=1");
        web_reqsuggestion.loadUrl("http://tagaboutit.com/RequestSuggestion/SendRequest?cityId="+ session.getcurrentcity() +"&platform=2&subcatid="+ subCategory +"&contactnumber=" + session.getUserMobile());

        return v;
    }
}
