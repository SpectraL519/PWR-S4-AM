package com.tictactoe.game.logic



enum class Figure {
    EMPTY {
        override fun next(): Figure = EMPTY
        override fun toString(): String = "none"
    },
    O {
        override fun next(): Figure = X
        override fun toString(): String = "player O"
    },
    X {
        override fun next(): Figure = O
        override fun toString(): String = "player X"
    };

    abstract fun next(): Figure
}

enum class Result {
    NONE { override fun toString(): String = "" },
    TIE { override fun toString(): String = "Tie" },
    O { override fun toString(): String = "Player O won!" },
    X { override fun toString(): String = "Player X won!" }
}

enum class VictoryType {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL_RIGHT,
    DIAGONAL_LEFT,
    NONE
}

class Status
    constructor(
        val result: Result,
        val victoryType: VictoryType,
        val index: Int = -1
    )