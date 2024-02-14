package com.example.rps

import android.media.Image
import android.widget.ImageView

class Figure (value: String, beats: String) {
    private var value: String? = null
    private var beats: String? = null

    init {
        this.value = value
        this.beats = beats
    }

    fun getValue (): String? {
        return this.value
    }

    fun hierarchy (node: String): Int {
        if (node == this.beats)
            return 1

        if (node == this.value)
            return 0

        return -1
    }
}