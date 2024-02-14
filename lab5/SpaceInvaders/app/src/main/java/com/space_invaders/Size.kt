package com.space_invaders

import android.util.DisplayMetrics

data class Size(
    val width: Int,
    val height: Int
) {
    constructor (displayMetrics: DisplayMetrics)
    : this(displayMetrics.widthPixels, displayMetrics.heightPixels)
}
