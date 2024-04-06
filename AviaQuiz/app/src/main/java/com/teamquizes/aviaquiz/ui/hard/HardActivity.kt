package com.teamquizes.aviaquiz.ui.hard

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
import com.teamquizes.aviaquiz.databinding.ActivityHardBinding
import com.teamquizes.aviaquiz.ui.finish.FinishActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HardActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityHardBinding>()
    private val sharedPreferences by lazy { getSharedPreferences("right_answer_prefs", Context.MODE_PRIVATE) }
    private var currentIndex = 0
    private var adapterIndex = 0
    private var rightAnswersCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard)

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
            pictureImageView.load("https://www.transportenvironment.org/wp-content/uploads/2021/08/AdobeStock_plane-runway.jpg") {
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
        private val rightAnswers = listOf(4, 2, 1, 4, 3, 2, 1, 2, 3, 2)

        private val answers = listOf(
            Answer(
                firstAnswer = "Eagle",
                secondAnswer = "Hedgehog",
                thirdAnswer = "Crocodile",
                fourthAnswer = "Bird"
            ),
            Answer(
                firstAnswer = "Ultrasonic",
                secondAnswer = "Gigosonic",
                thirdAnswer = "Sonic",
                fourthAnswer = "Hypersonic"
            ),
            Answer(
                firstAnswer = "A.F. Mozhaisky",
                secondAnswer = "I don't know",
                thirdAnswer = "Alexey Maresyev",
                fourthAnswer = "Leonardo Da Vinci"
            ),
            Answer(
                firstAnswer = "I don't know",
                secondAnswer = "Nikitich",
                thirdAnswer = "Who is it?",
                fourthAnswer = "Ilya Muromets"
            ),
            Answer(
                firstAnswer = "On the chassis",
                secondAnswer = "In the cabin",
                thirdAnswer = "On the main rudder",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "Olerons",
                secondAnswer = "Ailerons",
                thirdAnswer = "Elerons",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "S.V. Ilyushin",
                secondAnswer = "Alexey Maresyev",
                thirdAnswer = "A.V Tupolev",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "Aviation Day",
                secondAnswer = "Army Aviation Day",
                thirdAnswer = "Civil Aviation Day",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "U-2",
                secondAnswer = "Po-20",
                thirdAnswer = "Tu-134",
                fourthAnswer = "I don't know"
            ),
            Answer(
                firstAnswer = "I don't know",
                secondAnswer = "N.N. Dobronravov",
                thirdAnswer = "A.V. Tupolev",
                fourthAnswer = "Alexey Maresyev"
            )
        )

        private val questions = listOf(
            "What does aviation mean in Latin?",
            "What kind of aircraft does not exist in terms of flight speed?",
            "Which military figure and inventor built an airplane in 1883 at his own expense?",
            "In honor of which hero is the Russian passenger aircraft, developed under the guidance of aircraft designer I. I. Sikorsky, named after?",
            "Where is the numerator located on the plane?",
            "What is the name of the moving parts of the aircraft that serve to maintain lateral stability?",
            "Which Soviet aircraft designer received seven Stalin Prizes?",
            "Which of the following holidays is celebrated on October 28?",
            "What model aircraft was featured in the film \"The Incredible Adventures of Italians in Russia\"?",
            "“Under the wing of an airplane, the green sea of the taiga sings about something ...” Who is the author of these lines?"
        )
    }
}