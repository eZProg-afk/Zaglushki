package com.funnydevteam.whackthebandit.ui.gameResults

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.funnydevteam.whackthebandit.R
import com.funnydevteam.whackthebandit.databinding.ActivityGameResultsBinding
import com.funnydevteam.whackthebandit.ui.main.MainActivity
import com.funnydevteam.whackthebandit.ui.play.PlayActivity

class GameResultsActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityGameResultsBinding>()
    private val scorePrefs by lazy { getSharedPreferences("some_pregs", 0) }
    private val currentScore by lazy { intent.getIntExtra("score", 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_results)

        val bestScore = scorePrefs.getInt("best_score", 0)
        if (currentScore > bestScore) {
            scorePrefs.edit { putInt("best_score", currentScore) }
        }

        with(binding) {
            descriptionTextView.text = getString(R.string.score_format, currentScore)
            bestScoreTextView.text = getString(R.string.best_score_format, bestScore)

            playAgainButton.setOnClickListener {
                startActivity(Intent(this@GameResultsActivity, PlayActivity::class.java))
            }

            goBackToMenuButton.setOnClickListener {
                startActivity(Intent(this@GameResultsActivity, MainActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        /* no-op */
    }
}