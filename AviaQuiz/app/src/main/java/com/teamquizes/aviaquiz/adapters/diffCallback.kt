package com.teamquizes.aviaquiz.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

val diffCallback = object : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) =
        oldItem::class == newItem::class

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) =
        oldItem == newItem
}