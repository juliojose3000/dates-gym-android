package com.loaizasoftware.cachifitnesscenterandroid

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val BASE_URL_PRODUCTION = "https://cachi-fitness-center.web.app/home"
    private val BASE_URL_DEVELOP = "http://192.168.88.10:4200/home"
    private val PRODUCTION = true;


    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        mSwipeRefresh.setOnRefreshListener {
            mWebView.reload()
        }

        mWebView.webChromeClient = object : WebChromeClient() {}
        mWebView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                mSwipeRefresh.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mSwipeRefresh.isRefreshing = false
            }

        }

        val settings = mWebView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true

        if (PRODUCTION)
            mWebView.loadUrl(BASE_URL_PRODUCTION)
        else
            mWebView.loadUrl(BASE_URL_DEVELOP)

    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}