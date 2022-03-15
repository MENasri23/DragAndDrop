package com.example.draganddrop.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isInvisible")
fun View.isInvisible(invisible: Boolean) {
    visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}