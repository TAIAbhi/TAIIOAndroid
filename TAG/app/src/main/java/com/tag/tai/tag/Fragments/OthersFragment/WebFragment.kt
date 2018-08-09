package com.tag.tai.tag.Fragments.OthersFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.tag.tai.tag.R
import kotlinx.android.synthetic.main.fragment_web_view.*

const val DEFAULT_URL = "http://tagaboutit.com/Login/"
public const val KEY_REDIRECT_TO = "redirectTo"
class WebFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        web_feedback.apply {

            //setting client
            setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    view?.loadUrl(url);
                    return true
                }

                override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    Toast.makeText(getActivity(), "Error : " + description, Toast.LENGTH_SHORT).show();
                }
            });
            getSettings().setLoadsImagesAutomatically(true)
            getSettings().setJavaScriptEnabled(true)
            setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
            loadUrl(arguments?.getString(KEY_REDIRECT_TO, DEFAULT_URL))
        }
    }
}