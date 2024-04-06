package com.betnmoney.betsplayer.ui.betProcess

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.databinding.ActivitySelectTeamBetBinding
import com.betnmoney.betsplayer.ui.adapters.ItemChooseTeam
import com.betnmoney.betsplayer.ui.adapters.chooseTeamDelegate
import com.betnmoney.betsplayer.ui.adapters.diffCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.betnmoney.betsplayer.ui.adapters.ItemMatch

class SelectTeamBetActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivitySelectTeamBetBinding>()
    private val adapter = AsyncListDifferDelegationAdapter(diffCallback, chooseTeamDelegate)
    private val itemBet: ItemMatch by lazy { intent.getParcelableExtra("itemBet")!! }
    private var selectedTeamIndex = 0
    private val items by lazy {
        listOf(
            ItemChooseTeam(
                title = itemBet.firstTeamName,
                coeff = itemBet.firstCoeff.toString(),
                picture = itemBet.firstTeamLogo
            ),
            ItemChooseTeam(
                title = itemBet.secondTeamName,
                coeff = itemBet.secondCoeff.toString(),
                picture = itemBet.secondTeamLogo
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_team_bet)
        setUpIndicators()
        with(binding) {
            goBackButton.setOnClickListener {
                finish()
            }

            selectTeamButton.setOnClickListener {
                Intent(this@SelectTeamBetActivity, InputBetActivity::class.java).apply {
                    putExtra("itemBet", itemBet)
                    putExtra("selectedTeamIndex", selectedTeamIndex)
                }.also { startActivity(it) }
            }

            pageBackButton.setOnClickListener {
                onboardingViewPager.setCurrentItem(onboardingViewPager.currentItem - 1, true)
            }

            pageNextButton.setOnClickListener {
                onboardingViewPager.setCurrentItem(onboardingViewPager.currentItem + 1, true)
            }

            adapter.items = items
            onboardingViewPager.adapter = adapter
            onboardingViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    selectedTeamIndex = position
                    onboardingViewPager.setCurrentItem(position, true)
                    setCurrentIndicator(position)
                }
            })
            onboardingViewPager.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun setCurrentIndicator(position: Int) = with(binding) {
        val childCount = containerIndicatorsLinearLayout.childCount
        for (i in 0 until childCount) {
            val imageView = containerIndicatorsLinearLayout.getChildAt(i) as ImageView
            val indicatorIcon = getIndicatorIcon(i == position)
            imageView.setImageDrawable(indicatorIcon)
        }
    }

    private fun setUpIndicators() = with(binding) {
        val indicators = arrayOfNulls<ImageView>(2)
        val layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(8, 0, 8, 0) }
        for (index in (indicators.indices)) {
            indicators[index] = ImageView(this@SelectTeamBetActivity)
            indicators[index]?.apply {
                setImageDrawable(getIndicatorIcon(isActive = false))
                setLayoutParams(layoutParams)
                containerIndicatorsLinearLayout.addView(this)
            }
        }

        setCurrentIndicator(0)
    }

    private fun getIndicatorIcon(isActive: Boolean): Drawable? {
        return if (isActive) ResourcesCompat.getDrawable(
            resources,
            R.drawable.indicator_active_background,
            theme
        )
        else ResourcesCompat.getDrawable(
            resources,
            R.drawable.indicator_inactive_background,
            theme
        )
    }
}