package com.funnydevteam.whackthebandit.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class Base : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadArticles()
    }
}