package com.teamquizes.aviaquiz.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.teamquizes.aviaquiz.R
import com.teamquizes.aviaquiz.databinding.ActivityMainBinding
import com.teamquizes.aviaquiz.ui.easy.EasyActivity
import com.teamquizes.aviaquiz.ui.hard.HardActivity
import com.teamquizes.aviaquiz.ui.medium.MediumActivity
import com.teamquizes.aviaquiz.ui.rules.RulesActivity

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(binding) {
            easyButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, EasyActivity::class.java))
            }

            mediumButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, MediumActivity::class.java))
            }

            hardButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, HardActivity::class.java))
            }

            rulesButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, RulesActivity::class.java))
            }
        }
    }
}