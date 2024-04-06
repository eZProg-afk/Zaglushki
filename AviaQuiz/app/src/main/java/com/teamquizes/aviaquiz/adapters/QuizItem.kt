package com.teamquizes.aviaquiz.adapters

import android.os.Parcelable
import coil.load
import coil.transform.RoundedCornersTransformation
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.teamquizes.aviaquiz.databinding.ItemQuizBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizItem(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
) : ListItem, Parcelable

fun quizDelegate(onClick: (QuizItem) -> Unit) =
    adapterDelegateViewBinding<QuizItem, ListItem, ItemQuizBinding>({ inflater, container ->
        ItemQuizBinding.inflate(inflater, container, false)
    }) {
        with(binding) {
            root.setOnClickListener { onClick(item) }
            bind {
                sportTypeTextView.text = item.title
                descriptionTextView.text = item.description
                previewImageView.load(item.imageUrl) {
                    crossfade(enable = true)
                    transformations(RoundedCornersTransformation(16f))
                }
            }
        }
    }


