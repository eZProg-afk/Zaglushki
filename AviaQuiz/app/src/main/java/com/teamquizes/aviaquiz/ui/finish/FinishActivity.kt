package com.teamquizes.aviaquiz.ui.finish

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.teamquizes.aviaquiz.R
import com.teamquizes.aviaquiz.databinding.ActivityFinishBinding
import com.teamquizes.aviaquiz.ui.main.MainActivity

class FinishActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityFinishBinding>()
    private val sharedPreferences by lazy { getSharedPreferences("right_answer_prefs", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        with(binding) {
            val rightAnswersCount = sharedPreferences.getInt("rightAnswersCount", 0)
            val summaryAnswersCount = sharedPreferences.getInt("summaryAnswersCount", 10)
            val wrongAnswersCount = summaryAnswersCount - rightAnswersCount

            yourGoodResultsCountTextView.text = rightAnswersCount.toString()
            yourBadResultsCountTextView.text = wrongAnswersCount.toString()
            summaryAnswersCountTextView.text = summaryAnswersCount.toString()

            exitButton.setOnClickListener {
                sharedPreferences.edit().clear().apply()
                finishAffinity()
            }

            playAgainTextView.setOnClickListener {
                sharedPreferences.edit { clear() }
                startActivity(Intent(this@FinishActivity, MainActivity::class.java))
            }

            goToMenuButton.setOnClickListener {
                startActivity(Intent(this@FinishActivity, MainActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        sharedPreferences.edit { clear() }
        finishAffinity()
    }
}