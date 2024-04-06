package com.betnmoney.betsplayer.ui.adapters

import android.os.Parcelable
import androidx.room.PrimaryKey
import coil.load
import coil.transform.RoundedCornersTransformation
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.databinding.ItemMatchBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemMatch(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firstTeamName: String,
    val firstTeamLogo: String,
    val secondTeamName: String,
    val secondTeamLogo: String,
    val firstCoeff: Float,
    val secondCoeff: Float,
    val resultCoeff: Float,
    var isSomethingBet: Boolean = false,
    var selectedTeam: Int = 0,
    var coeff: Float = 0.0f,
    var moneyBet: Int = 0,
) : ListItem, Parcelable

fun matchesDelegate(onClick: (ItemMatch) -> Unit) =
    adapterDelegateViewBinding<ItemMatch, ListItem, ItemMatchBinding>({ inflater, container ->
        ItemMatchBinding.inflate(inflater, container, false)
    }) {
        with(binding) {
            readMoreTextView.setOnClickListener { onClick.invoke(item) }
            bind {
                teamsTextView.text = "${item.firstTeamName} VS ${item.secondTeamName}"
                firstTeamLogoImageView.load(item.firstTeamLogo) {
                    transformations(RoundedCornersTransformation(16f))
                    placeholder(R.drawable.ic_placeholder)
                    crossfade(enable = true)
                }
                secondTeamLogoImageView.load(item.secondTeamLogo) {
                    transformations(RoundedCornersTransformation(16f))
                    placeholder(R.drawable.ic_placeholder)
                    crossfade(enable = true)
                }
            }
        }
    }