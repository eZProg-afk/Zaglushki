package com.teamquizes.aviaquiz.ui.easy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import com.teamquizes.aviaquiz.R
import com.teamquizes.aviaquiz.adapters.Answer
import com.teamquizes.aviaquiz.databinding.ActivityEasyBinding
import com.teamquizes.aviaquiz.ui.finish.FinishActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EasyActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityEasyBinding>()
    private val sharedPreferences by lazy { getSharedPreferences("right_answer_prefs", Context.MODE_PRIVATE) }
    private var currentIndex = 0
    private var adapterIndex = 0
    private var rightAnswersCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy)

        loadNextQuestion()
        with(binding) {
            answerFirstTextView.setOnClickListener {
                answer(1)
            }

            answerSecondTextView.setOnClickListener {
                answer(2)
            }

            answerThirdTextView.setOnClickListener {
                answer(3)
            }

            answerFourthTextView.setOnClickListener {
                answer(4)
            }
        }
    }

    private fun answer(answerNumber: Int) {
        if (currentIndex < 9) {
            if (rightAnswers[currentIndex] == answerNumber) {
                currentIndex++
                adapterIndex += 4
                rightAnswersCount++
                loadNextQuestion()
            } else {
                currentIndex++
                adapterIndex += 4
                loadNextQuestion()
            }
        } else {
            Intent(this, FinishActivity::class.java).apply {
                sharedPreferences.edit {
                    putInt("rightAnswersCount", rightAnswersCount)
                    putInt("summaryAnswersCount", 10)
                }
            }.also { startActivity(it) }
        }
    }

    private fun loadNextQuestion() = lifecycleScope.launch {
        delay(200L)
        with(binding) {
            pictureImageView.load("https://imageio.forbes.com/specials-images/imageserve/5feb3ee6f87b77880244f345/Passenger-airplane-landing-at-sunset/960x0.jpg?format=jpg&width=960") {
                crossfade(enable = true)
                transformations(RoundedCornersTransformation(12f))
            }
            answerGuessCountFormatTextView.text = getString(R.string.answer_guess_count_format, currentIndex.inc(), 10)

            questionTextView.text = questions[currentIndex]
            answerFirstTextView.text = answers[currentIndex].firstAnswer
            answerSecondTextView.text = answers[currentIndex].secondAnswer
            answerThirdTextView.text = answers[currentIndex].thirdAnswer
            answerFourthTextView.text = answers[currentIndex].fourthAnswer
        }
    }

    companion object {
        private val rightAnswers = listOf(1, 3, 2, 3, 1, 1, 1, 3, 2, 1)

        private val answers = listOf(
            Answer(
                firstAnswer = "Fixed wing",
                secondAnswer = "Engine availability",
                thirdAnswer = "Tail",
                fourthAnswer = "Chassis"
            ),
            Answer(
                firstAnswer = "N. M. Sokovnin",
                secondAnswer = "Leonardo da Vinci",
                thirdAnswer = "A. V. Evald",
                fourthAnswer = "Nikola Tesla"
            ),
            Answer(
                firstAnswer = "Spotters",
                secondAnswer = "Firefighters",
                thirdAnswer = "Patrol",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "8",
                secondAnswer = "10",
                thirdAnswer = "12",
                fourthAnswer = "14"
            ),
            Answer(
                firstAnswer = "Duck",
                secondAnswer = "Chicken",
                thirdAnswer = "Sparrow",
                fourthAnswer = "Eagle"
            ),
            Answer(
                firstAnswer = "Land, ship, seaplane, flying submarine",
                secondAnswer = "Amphibious, float, \"flying boats\"",
                thirdAnswer = "Narrow body, wide body",
                fourthAnswer = "Narrow body only"
            ),
            Answer(
                firstAnswer = "With ski chassis",
                secondAnswer = "Float",
                thirdAnswer = "Amphibians",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "Subsonic",
                secondAnswer = "Transonic",
                thirdAnswer = "Sonic",
                fourthAnswer = "Ultrasonic"
            ),
            Answer(
                firstAnswer = "A. S. Kudashev",
                secondAnswer = "N. E. Zhukovsky",
                thirdAnswer = "I. I. Sikorsky",
                fourthAnswer = "Einstein"
            ),
            Answer(
                firstAnswer = "\"Flyer-1\"",
                secondAnswer = "\"Rightolet\"",
                thirdAnswer = "\"Wind-2\"",
                fourthAnswer = "I don't know"
            )
        )

        private val questions = listOf(
            "What part of an airplane distinguishes it from an airplane and a helicopter?",
            "Who first used the word \"airplane\" in a meaning close to the modern one?",
            "Specify a civil aircraft.",
            "What is the maximum number of engines an aircraft has?",
            "What is the name of the aerodynamic scheme, in which the horizontal tail is located ahead of the main wing?",
            "What are the types of aircraft landing organs?",
            "Specify the aircraft that fits the classification \"land\" according to the type of chassis.",
            "What type of aircraft does not exist in terms of flight speed?",
            "Which scientist is considered the father of aviation?",
            "Indicate the name of the first aircraft that was able to independently make a stable controlled horizontal flight."
        )
    }
}