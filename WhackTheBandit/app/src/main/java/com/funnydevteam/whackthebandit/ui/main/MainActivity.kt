package com.funnydevteam.whackthebandit.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.funnydevteam.whackthebandit.R
import com.funnydevteam.whackthebandit.databinding.ActivityMainBinding
import com.funnydevteam.whackthebandit.ui.play.PlayActivity

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(binding) {
            playGameButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, PlayActivity::class.java))
            }

            exitGameButton.setOnClickListener {
                finishAffinity()
            }
        }
    }

    override fun onBackPressed() {
        /* no-op */
    }
}