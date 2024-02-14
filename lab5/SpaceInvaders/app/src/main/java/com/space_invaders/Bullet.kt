package com.space_invaders

import android.graphics.RectF


class Bullet
    constructor(
        private val size: Size,
        heightModifier: Float = 20f,
        private val speed: Float = 350f
    ) {

    private val width = 2
    private val height = this.size.height / heightModifier

    val position = RectF()
    var active = false
    private var direction: Direction = Direction.NONE

    fun shoot (x: Float, y: Float, direction: Direction) : Boolean {
        if (
            !this.active &&
            (direction == Direction.UP || direction == Direction.DOWN)
        ) {
            this.position.left = x
            this.position.right = x + this.width
            this.position.top = y
            this.position.bottom = y + this.height

            this.direction = direction
            this.active = true
            return true
        }

        return false
    }

    fun update (fps: Long) {
        val movement = this.speed / fps

        when (this.direction) {
            Direction.UP -> {
                if (this.position.bottom > 0) {
                    this.position.bottom -= movement
                    this.position.top = this.position.bottom - this.height
                }
            }
            Direction.DOWN -> {
                if (this.position.top < this.size.height) {
                    this.position.top += movement
                    this.position.bottom = this.position.top + this.height
                }
            }
            else -> {}
        }
    }
}