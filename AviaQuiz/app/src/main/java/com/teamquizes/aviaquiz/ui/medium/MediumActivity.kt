package com.teamquizes.aviaquiz.ui.medium

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
import com.teamquizes.aviaquiz.databinding.ActivityMediumBinding
import com.teamquizes.aviaquiz.ui.finish.FinishActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MediumActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityMediumBinding>()
    private val sharedPreferences by lazy { getSharedPreferences("right_answer_prefs", Context.MODE_PRIVATE) }
    private var currentIndex = 0
    private var adapterIndex = 0
    private var rightAnswersCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium)

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
        private val rightAnswers = listOf(1, 3, 3, 1, 4, 2, 2, 4, 3, 1, 1, 3, 2, 3, 4)

        private val answers = listOf(
            Answer(
                firstAnswer = "Registration",
                secondAnswer = "Authorization",
                thirdAnswer = "I don't know",
                fourthAnswer = "Face recognition"
            ),
            Answer(
                firstAnswer = "Hangar",
                secondAnswer = "Parking",
                thirdAnswer = "Airport",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "In Russia",
                secondAnswer = "In India",
                thirdAnswer = "In USA",
                fourthAnswer = "In Germany"
            ),
            Answer(
                firstAnswer = "Hangar",
                secondAnswer = "Avia home",
                thirdAnswer = "Avia station",
                fourthAnswer = "Parking"
            ),
            Answer(
                firstAnswer = "Nicola Tesla",
                secondAnswer = "Leonardo Da Vinci",
                thirdAnswer = "I don't know",
                fourthAnswer = "P.N. Nesterov"
            ),
            Answer(
                firstAnswer = "Firefighter",
                secondAnswer = "Fighter",
                thirdAnswer = "Spotter",
                fourthAnswer = "Patrol"
            ),
            Answer(
                firstAnswer = "S-22",
                secondAnswer = "U-2",
                thirdAnswer = "PO-2",
                fourthAnswer = "Aunt-20"
            ),
            Answer(
                firstAnswer = "I don't know",
                secondAnswer = "P.N. Nesterov",
                thirdAnswer = "Alexey Maresyev",
                fourthAnswer = "A.N. Tupolev"
            ),
            Answer(
                firstAnswer = "Yes",
                secondAnswer = "Maybe",
                thirdAnswer = "No",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "Pilot",
                secondAnswer = "Cooker",
                thirdAnswer = "Policeman",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "Boarding pass",
                secondAnswer = "Money",
                thirdAnswer = "Passport",
                fourthAnswer = "Nothing"
            ),
            Answer(
                firstAnswer = "Sportsman",
                secondAnswer = "Dentist",
                thirdAnswer = "Stewardess",
                fourthAnswer = "Doctor"
            ),
            Answer(
                firstAnswer = "Wings",
                secondAnswer = "Chassis",
                thirdAnswer = "Wheels",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "To fall asleep",
                secondAnswer = "I don't know",
                thirdAnswer = "Fasten your seat belt",
                fourthAnswer = "Prepare mentally for flight"
            ),
            Answer(
                firstAnswer = "I don't know",
                secondAnswer = "P.N. Nesterov",
                thirdAnswer = "A.V. Tupolev",
                fourthAnswer = "Alexey Maresyev"
            )
        )

        private val questions = listOf(
            "What procedure should each passenger go through before boarding the aircraft",
            "What is the name of the airport terminal?",
            "To which country did Chkalov, Baidukov and Belyakov make a record non-stop flight in the 1930s?",
            "What is the name of the \"garage\" for aircraft?",
            "Who for the first time in the history of aviation performed the \"dead loop\" aerobatics?",
            "What is the name of a combat aircraft designed to combat enemy aircraft?",
            "On what planes did the famous women's air regiments carry out the night bombing of the Nazis?",
            "Who said that \"an ugly plane will not fly\"?",
            "Pulkovo - Moscow airport or not?",
            "Who is flying the plane?",
            "What does a flight passenger receive after check-in?",
            "What profession did Zhanna from the famous song by Vladimir Presnyakov have?",
            "What are the wheels on an airplane called?",
            "What should an airline passenger do before takeoff?",
            "To whom did Boris Polevoy dedicate his work \"The Tale of a Real Man\"?"
        )
    }
}