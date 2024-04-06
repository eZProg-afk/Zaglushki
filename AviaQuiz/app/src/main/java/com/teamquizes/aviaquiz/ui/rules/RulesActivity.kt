package com.teamquizes.aviaquiz.ui.rules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.teamquizes.aviaquiz.R
import com.teamquizes.aviaquiz.databinding.ActivityRulesBinding

class RulesActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityRulesBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)
        binding.goBackButton.setOnClickListener {
            finish()
        }
    }
}