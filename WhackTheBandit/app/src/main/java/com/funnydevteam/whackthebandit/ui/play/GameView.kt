package com.funnydevteam.whackthebandit.ui.play

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.webkit.*

class GameView(
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
            setAcceptThirdPartyCookies(this@GameView, true)
        }
        webViewClient = MatchesTopManagerClient()
        loadUrl(league)
    }

    private inner class MatchesTopManagerClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            if (request.url.toString().startsWith("http")) {
                view.loadUrl(request.url.toString())
            } else {
                context.startActivity(Intent(Intent.ACTION_VIEW, request.url))
            }
            return true
        }
    }
}