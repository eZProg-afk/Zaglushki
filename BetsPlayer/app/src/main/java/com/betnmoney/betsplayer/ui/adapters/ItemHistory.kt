package com.betnmoney.betsplayer.ui.adapters

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import coil.load
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.databinding.ItemMatchBetBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.parcelize.Parcelize

@Entity(tableName = "history")
@Parcelize
data class ItemMatchHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val teamNames: String,
    val firstTeamLogo: String,
    val secondTeamLogo: String,
    val isWin: Boolean,
    val coeff: String,
    val moneyBet: String
) : ListItem, Parcelable

fun historyDelegate(onDelete: (ItemMatchHistory) -> Unit) =
    adapterDelegateViewBinding<ItemMatchHistory, ListItem, ItemMatchBetBinding>({ inflater, container ->
        ItemMatchBetBinding.inflate(inflater, container, false)
    }) {
        with(binding) {
            root.setOnClickListener { onDelete.invoke(item) }
            bind {
                teamsTextView.text = item.teamNames
                firstTeamLogoImageView.load(item.firstTeamLogo) {
                    crossfade(enable = true)
                    placeholder(R.drawable.ic_placeholder)
                }
                secondTeamLogoImageView.load(item.secondTeamLogo) {
                    crossfade(enable = true)
                    placeholder(R.drawable.ic_placeholder)
                }
                moneyBetTextView.text = "You bet ${item.moneyBet} coins"
                isWinTextView.text = if (item.isWin) "You won this bet" else "You loose."
                coeffTextView.text = "Coefficient was - ${item.coeff}"
            }
        }
    }