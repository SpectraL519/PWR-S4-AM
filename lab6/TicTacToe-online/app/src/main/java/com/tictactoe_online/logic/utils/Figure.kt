package com.tictactoe_online.logic.utils

import com.tictactoe_online.R


enum class Figure {
    EMPTY {
        override fun next(): Figure = EMPTY
        override fun toString(): String = "none"
        override val imageResource: Int = android.R.color.transparent
    },

    O {
        override fun next(): Figure = X
        override fun toString(): String = "O"
        override val imageResource: Int = R.drawable.circle
    },

    X {
        override fun next(): Figure = O
        override fun toString(): String = "X"
        override val imageResource: Int = R.drawable.cross
    };

    abstract fun next(): Figure
    abstract val imageResource: Int
}