package com.betnmoney.betsplayer.ui.betProcess

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.webkit.*

class BetView(
    context: Context,
    attributeSet: AttributeSet
) : WebView(context, attributeSet) {

    @SuppressLint("SetJavaScriptEnabled")
    fun setGame(league: String) {
        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            allowFileAccess = true
            allowContentAccess = true
            javaScriptCanOpenWindowsAutomatically = true
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        CookieManager.getInstance().apply {
            setAcceptCookie(true)
            setAcceptThirdPartyCookies(this@BetView, true)
        }
        webViewClient = MatchesTopManagerClient()
        loadUrl(league)
    }

    private inner class MatchesTopManagerClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            if (request.url.toString().startsWith("http")) {
                view.loadUrl(request.url.toString())
            } else {
                context.startActivity(Intent(Intent.ACTION_VIEW, request.url))
            }
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith("http")) {
                view.loadUrl(url)
            } else {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
            return true
        }
    }
}