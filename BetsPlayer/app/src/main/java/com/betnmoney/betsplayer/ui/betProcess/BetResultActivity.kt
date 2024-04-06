package com.betnmoney.betsplayer.ui.betProcess

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.data.HistoryDao
import com.betnmoney.betsplayer.databinding.ActivityBetResultBinding
import com.betnmoney.betsplayer.ui.adapters.ItemMatchHistory
import com.betnmoney.betsplayer.ui.main.MainActivity
import com.betnmoney.betsplayer.ui.main.MainActivity.Companion.USER_BALANCE
import com.betnmoney.betsplayer.ui.adapters.ItemMatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import kotlin.random.Random

class BetResultActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityBetResultBinding>()
    private val historyDao by inject<HistoryDao>()
    private val selectedTeamIndex by lazy { intent.getIntExtra("selectedTeamIndex", 0) }
    private val moneyBet by lazy { intent.getIntExtra("betMoney", 0) }
    private val itemBet: ItemMatch by lazy { intent.getParcelableExtra("itemBet")!! }
    private val firstTeamPoints by lazy { Random.nextInt(0, 8) }
    private val secondTeamPoints by lazy { Random.nextInt(0, 8) }
    private val coeff by lazy {
        when (selectedTeamIndex) {
            0 -> itemBet.firstCoeff
            1 -> itemBet.secondCoeff
            else -> itemBet.firstCoeff
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bet_result)


        with(binding) {
            var isWin = false
            var winningTeamIndex = 0

            when (selectedTeamIndex) {
                0 -> {
                    isWin = if (firstTeamPoints > secondTeamPoints) {
                        winningTeamIndex = 0
                        true
                    } else if (firstTeamPoints < secondTeamPoints) {
                        winningTeamIndex = 1
                        false
                    } else {
                        false
                    }
                }
                1 -> {
                    isWin = if (secondTeamPoints > firstTeamPoints) {
                        winningTeamIndex = 1
                        true
                    } else if (secondTeamPoints < firstTeamPoints) {
                        winningTeamIndex = 0
                        false
                    } else {
                        false
                    }
                }
            }

            winningTeamNameTextView.text = "Winning team - ${
                when (winningTeamIndex) {
                    0 -> itemBet.firstTeamName
                    1 -> itemBet.secondTeamName
                    else -> itemBet.firstTeamName
                }
            }"

            winningTeamLogoImageView.load(
                when (winningTeamIndex) {
                    0 -> itemBet.firstTeamLogo
                    1 -> itemBet.secondTeamLogo
                    else -> itemBet.firstTeamLogo
                }
            ) {
                crossfade(enable = true)
                transformations(RoundedCornersTransformation(8f))
            }

            coeffTextView.text = "Coefficient - ${
                when (winningTeamIndex) {
                    0 -> itemBet.firstCoeff
                    1 -> itemBet.secondCoeff
                    else -> itemBet.firstCoeff
                }
            }"

            resultTextView.text = when (isWin) {
                true -> {
                    USER_BALANCE += moneyBet
                    "You win and earn $moneyBet coins. Very well!"
                }
                false -> {
                    USER_BALANCE -= moneyBet
                    "You loose and lost $moneyBet coins. Don't worry, you have more $USER_BALANCE coins!"
                }
            }

            goBackButton.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    historyDao.insert(
                        ItemMatchHistory(
                            teamNames = "${itemBet.firstTeamName} VS ${itemBet.secondTeamName}",
                            firstTeamLogo = itemBet.firstTeamLogo,
                            secondTeamLogo = itemBet.secondTeamLogo,
                            isWin = isWin,
                            coeff = coeff.toString(),
                            moneyBet = moneyBet.toString()
                        )
                    )
                    withContext(Dispatchers.Main) {
                        startActivity(Intent(this@BetResultActivity, MainActivity::class.java))
                    }
                }
            }
        }
    }
}