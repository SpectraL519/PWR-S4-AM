package com.space_invaders

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import java.util.*


class Invader
    constructor(
        context: Context,
        row: Int,
        column: Int,
        private val size: Size,
    ) {

    var alive = true

    val width = this.size.width / 35f
    val height = this.size.height / 35f
    private val padding = this.size.width / 45

    var position = RectF(
        column * (this.width + this.padding),
        125 + row * (this.width + this.padding / 4),
        column * (this.width + this.padding) + this.width,
        125 + row * (this.width + this.padding / 4) + this.height
    )

    private var speed = 50f
    private var direction = Direction.RIGHT

    private val generator = Random()

    init {
        aliveInvaders++

        bitmapA = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.invader_a
            ),
            this.width.toInt(),
            this.height.toInt(),
            false
        )

        bitmapB = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.invader_b
            ),
            this.width.toInt(),
            this.height.toInt(),
            false
        )
    }

    fun update (fps: Long) {
        val movement = this.speed / fps

        when (this.direction) {
            Direction.LEFT -> {
                if (this.position.right > 0) {
                    this.position.right -= movement
                    this.position.left = this.position.right - this.width
                }
            }
            Direction.RIGHT -> {
                if (this.position.left < this.size.width) {
                    this.position.left += movement
                    this.position.right = this.position.left + this.width
                }
            }
            else -> {}
        }
    }

    fun moveForwardAndReverse (wave: Int) {
        this.direction =
            if (this.direction == Direction.LEFT)
                Direction.RIGHT
            else
                Direction.LEFT

        this.position.top += this.height
        this.position.bottom += this.height

        this.speed *= (1.1f + (wave / 20f))
    }

    fun aim (
        spaceFighterX: Float,
        spaceFighterW: Float,
        wave: Int
    ) : Boolean {
        var number: Int

        if (
            (spaceFighterX > this.position.left &&
            spaceFighterX < this.position.left + this.width) ||
            (spaceFighterX + spaceFighterW > this.position.left &&
            spaceFighterX + spaceFighterW < this.position.left + this.width)
        ) {
            number = this.generator.nextInt((100 * aliveInvaders) / wave)
            if (number == 0)
                return true
        }

        // fire randomly
        number = generator.nextInt(150 * aliveInvaders)
        return (number == 0)
    }

    companion object {
        var aliveInvaders = 0

        lateinit var bitmapA: Bitmap
        lateinit var bitmapB: Bitmap
    }
}