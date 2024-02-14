package com.space_invaders

import android.graphics.RectF


class ShieldPiece
    constructor(
        row: Int,
        column: Int,
        shieldIdx: Int,
        size: Size
    ) {

    var undamaged = true

    private val width = size.width / 120f
    private val height = size.height / 80f
    private val leftShieldPadding = (size.width * (shieldIdx + 1) / (NUM_SHIELDS + 1))
    private val startHeight = size.height / 5f * 4f

    val position = RectF(
        this.leftShieldPadding + column * this.width,
        this.startHeight + row * this.height,
        this.leftShieldPadding + (column + 1) * this.width,
        this.startHeight + (row + 1) * this.height
    )

    companion object {
        const val NUM_SHIELDS = 4
        const val NUM_ROWS = 8
        const val NUM_COLUMNS = 12
    }
}