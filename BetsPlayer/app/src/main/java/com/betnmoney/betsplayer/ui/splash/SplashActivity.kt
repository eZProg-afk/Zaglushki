package com.betnmoney.betsplayer.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.ui.betHistory.loadArticles

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadArticles()
    }
}