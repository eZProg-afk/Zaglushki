package com.betnmoney.betsplayer.ui.betProcess

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.databinding.ActivityInputBetBinding
import com.betnmoney.betsplayer.ui.extensions.changeTextColor
import com.betnmoney.betsplayer.ui.main.MainActivity.Companion.USER_BALANCE
import com.betnmoney.betsplayer.ui.adapters.ItemMatch

class InputBetActivity : AppCompatActivity(R.layout.activity_input_bet) {

    private val binding by viewBinding<ActivityInputBetBinding>()
    private var selectedBet = 50
    private val itemBet: ItemMatch by lazy { intent.getParcelableExtra("itemBet")!! }
    private val selectedTeamIndex by lazy { intent.getIntExtra("selectedTeamIndex", 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_bet)

        with(binding) {
            betNumberPicker.apply {
                changeTextColor(Color.WHITE)
                minValue = 50
                maxValue = 5000
                setOnValueChangedListener { _, _, newVal ->
                    selectedBet = newVal
                }
            }

            nextButton.setOnClickListener {
                if (selectedBet >= USER_BALANCE) {
                    Toast.makeText(
                        this@InputBetActivity,
                        "The bet must be less than your balance!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Intent(this@InputBetActivity, BetResultActivity::class.java).apply {
                        putExtra("selectedTeamIndex", selectedTeamIndex)
                        putExtra("betMoney", selectedBet)
                        putExtra("itemBet", itemBet)
                    }.also { startActivity(it) }
                }
            }

            goBackButton.setOnClickListener {
                finish()
            }
        }
    }
}