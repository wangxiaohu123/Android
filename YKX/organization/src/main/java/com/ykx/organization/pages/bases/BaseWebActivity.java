package com.ykx.organization.pages.bases;

import android.os.Bundle;
import android.webkit.WebView;

import com.ykx.baselibs.pages.BaseActivity;
import com.ykx.organization.R;

public class BaseWebActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web);

        initUI();
    }

    private void initUI(){

        webView=(WebView) findViewById(R.id.base_webview);
        webView.loadUrl(getLoadUrl());
    }

    protected String getLoadUrl(){

        return "";
    }

}
