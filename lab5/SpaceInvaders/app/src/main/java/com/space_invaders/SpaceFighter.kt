package com.space_invaders

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF


class SpaceFighter
    constructor(
        context: Context,
        private val size: Size
    ) {

    var bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.space_fighter
    )

    // ship size
    val width = this.size.width / 20f
    val height = this.size.height / 20f

    // ship position on the screen
    val position = RectF(
        this.size.width / 2f,
        this.size.height - this.height,
        this.size.width / 2f + width,
        this.size.height.toFloat()
    )

    private val speed = 450f

    init {
        this.bitmap = Bitmap.createScaledBitmap(
            this.bitmap,
            this.width.toInt(),
            this.height.toInt(),
            false
        )
    }

    fun move (direction: Direction, fps: Long) {
        val movement = this.speed / fps

        when (direction) {
            Direction.LEFT -> {
                if (this.position.left > movement) {
                    this.position.left -= movement
                    this.position.right = this.position.left + this.width
                }
            }
            Direction.RIGHT -> {
                if (this.position.right < this.size.width - movement) {
                    this.position.right += movement
                    this.position.left = this.position.right - this.width
                }
            }
            else -> {}
        }
    }
}