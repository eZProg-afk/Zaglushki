package com.funnydevteam.whackthebandit.ui.play

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.funnydevteam.whackthebandit.R
import com.funnydevteam.whackthebandit.databinding.ActivityPlayBinding
import com.funnydevteam.whackthebandit.ui.gameResults.GameResultsActivity
import com.funnydevteam.whackthebandit.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timer
import kotlin.random.Random

class PlayActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityPlayBinding>()
    private var isKilled = false
    private var score = 0
    private var sherifsKilled = 0
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        binding.currentScoreTextView.text = getString(R.string.current_score_format, score)
        binding.penaltyScoreTextView.text = getString(R.string.penalty_score_format, sherifsKilled)
        binding.goBackTextView.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        timer = timer(initialDelay = 1000, period = 2000) {
            isKilled = false
            createPlayer(Random.nextInt(1, 10))
        }
    }

    private fun createPlayer(index: Int) {
        val alphaFadeInAnimation = AlphaAnimation(0f, 1f).apply { duration = 1000 }
        val isBandit = Random.nextInt(0, 2) == 1

        lifecycleScope.launch(Dispatchers.Main) {
            with(binding) {
                firstImageView.isEnabled = true
                secondImageView.isEnabled = true
                thirdImageView.isEnabled = true
                fourthImageView.isEnabled = true
                fifthImageView.isEnabled = true
                sixthImageView.isEnabled = true
                seventhImageView.isEnabled = true
                eightImageView.isEnabled = true
                nineImageView.isEnabled = true

                val imageViewToWorkWith = when (index) {
                    1 -> {
                        secondImageView.isEnabled = false
                        thirdImageView.isEnabled = false
                        fourthImageView.isEnabled = false
                        fifthImageView.isEnabled = false
                        sixthImageView.isEnabled = false
                        seventhImageView.isEnabled = false
                        eightImageView.isEnabled = false
                        nineImageView.isEnabled = false
                        firstImageView
                    }
                    2 -> {
                        firstImageView.isEnabled = false
                        thirdImageView.isEnabled = false
                        fourthImageView.isEnabled = false
                        fifthImageView.isEnabled = false
                        sixthImageView.isEnabled = false
                        seventhImageView.isEnabled = false
                        eightImageView.isEnabled = false
                        nineImageView.isEnabled = false
                        secondImageView
                    }
                    3 -> {
                        firstImageView.isEnabled = false
                        secondImageView.isEnabled = false
                        fourthImageView.isEnabled = false
                        fifthImageView.isEnabled = false
                        sixthImageView.isEnabled = false
                        seventhImageView.isEnabled = false
                        eightImageView.isEnabled = false
                        nineImageView.isEnabled = false
                        thirdImageView
                    }
                    4 -> {
                        firstImageView.isEnabled = false
                        secondImageView.isEnabled = false
                        thirdImageView.isEnabled = false
                        fifthImageView.isEnabled = false
                        sixthImageView.isEnabled = false
                        seventhImageView.isEnabled = false
                        eightImageView.isEnabled = false
                        nineImageView.isEnabled = false
                        fourthImageView
                    }
                    5 -> {
                        firstImageView.isEnabled = false
                        secondImageView.isEnabled = false
                        thirdImageView.isEnabled = false
                        fourthImageView.isEnabled = false
                        sixthImageView.isEnabled = false
                        seventhImageView.isEnabled = false
                        eightImageView.isEnabled = false
                        nineImageView.isEnabled = false
                        fifthImageView
                    }
                    6 -> {
                        firstImageView.isEnabled = false
                        secondImageView.isEnabled = false
                        thirdImageView.isEnabled = false
                        fourthImageView.isEnabled = false
                        fifthImageView.isEnabled = false
                        seventhImageView.isEnabled = false
                        eightImageView.isEnabled = false
                        nineImageView.isEnabled = false
                        sixthImageView
                    }
                    7 -> {
                        firstImageView.isEnabled = false
                        secondImageView.isEnabled = false
                        thirdImageView.isEnabled = false
                        fourthImageView.isEnabled = false
                        fifthImageView.isEnabled = false
                        sixthImageView.isEnabled = false
                        eightImageView.isEnabled = false
                        nineImageView.isEnabled = false
                        seventhImageView
                    }
                    8 -> {
                        firstImageView.isEnabled = false
                        secondImageView.isEnabled = false
                        thirdImageView.isEnabled = false
                        fourthImageView.isEnabled = false
                        fifthImageView.isEnabled = false
                        sixthImageView.isEnabled = false
                        seventhImageView.isEnabled = false
                        nineImageView.isEnabled = false
                        eightImageView
                    }
                    9 -> {
                        firstImageView.isEnabled = false
                        secondImageView.isEnabled = false
                        thirdImageView.isEnabled = false
                        fourthImageView.isEnabled = false
                        fifthImageView.isEnabled = false
                        sixthImageView.isEnabled = false
                        seventhImageView.isEnabled = false
                        secondImageView.isEnabled = false
                        nineImageView
                    }
                    else -> firstImageView
                }

                imageViewToWorkWith.setImageResource(if (isBandit) R.drawable.bandit else R.drawable.sherif)
                imageViewToWorkWith.startAnimation(alphaFadeInAnimation)

                imageViewToWorkWith.setOnClickListener {
                    killTapped(index, !isBandit)
                }

                lifecycleScope.launch(Dispatchers.Main) {
                    val alphaFadeOutAnimation = AlphaAnimation(1f, 0f).apply {
                        duration = 1000
                    }
                    alphaFadeOutAnimation.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            imageViewToWorkWith.setImageResource(0)
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                        }
                    })

                    alphaFadeInAnimation.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(animation: Animation?) {
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            lifecycleScope.launch {
                                if (!isKilled) {
                                    imageViewToWorkWith.startAnimation(alphaFadeOutAnimation)
                                } else {
                                    delay(1000)
                                    imageViewToWorkWith.startAnimation(alphaFadeOutAnimation)
                                }
                            }
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                        }
                    })
                }
            }
        }
    }

    private fun killTapped(index: Int, isSherif: Boolean) {
        val mediaPlayer = MediaPlayer.create(this, R.raw.pistol_sound)
        mediaPlayer.start()
        isKilled = true
        with(binding) {
            val imageViewToWorkWith = when (index) {
                1 -> firstImageView
                2 -> secondImageView
                3 -> thirdImageView
                4 -> fourthImageView
                5 -> fifthImageView
                6 -> sixthImageView
                7 -> seventhImageView
                8 -> eightImageView
                9 -> nineImageView
                else -> firstImageView
            }

            imageViewToWorkWith.isEnabled = false

            mediaPlayer.setOnCompletionListener {
                imageViewToWorkWith.setImageResource(R.drawable.blood)

                if (sherifsKilled == 3) {
                    Intent(this@PlayActivity, GameResultsActivity::class.java).apply {
                        putExtra("score", score)
                    }.also { startActivity(it) }
                } else if (isSherif) {
                    sherifsKilled += 1
                } else {
                    score++
                }

                with(binding) {
                    currentScoreTextView.text = getString(R.string.current_score_format, score)
                    penaltyScoreTextView.text = getString(R.string.penalty_score_format, sherifsKilled)
                }
            }
        }
    }

    private fun resetGame() = with(binding) {
        isKilled = false
        score = 0
        sherifsKilled = 0
        firstImageView.setImageResource(0)
        secondImageView.setImageResource(0)
        thirdImageView.setImageResource(0)
        fourthImageView.setImageResource(0)
        fifthImageView.setImageResource(0)
        sixthImageView.setImageResource(0)
        seventhImageView.setImageResource(0)
        eightImageView.setImageResource(0)
        nineImageView.setImageResource(0)
        timer?.cancel()
        timer = null
    }

    override fun onBackPressed() {
        /* no-op */
    }
}