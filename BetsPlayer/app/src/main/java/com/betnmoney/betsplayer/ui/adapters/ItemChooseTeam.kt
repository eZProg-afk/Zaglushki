package com.betnmoney.betsplayer.ui.adapters

import coil.load
import coil.transform.RoundedCornersTransformation
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.databinding.ItemChooseTeamContainerBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

open class ItemChooseTeam(
    val title: String,
    val coeff: String,
    val picture: String
) : ListItem

val chooseTeamDelegate =
    adapterDelegateViewBinding<ItemChooseTeam, ListItem, ItemChooseTeamContainerBinding>({ inflater, container ->
        ItemChooseTeamContainerBinding.inflate(inflater, container, false)
    }) {
        with(binding) {
            bind {
                titleTextView.text = item.title
                coeffTextView.text = item.coeff
                onboardingPictureImageView.load(item.picture) {
                    crossfade(enable = true)
                    placeholder(R.drawable.ic_placeholder)
                    transformations(RoundedCornersTransformation(32f))
                }
            }
        }
    }



