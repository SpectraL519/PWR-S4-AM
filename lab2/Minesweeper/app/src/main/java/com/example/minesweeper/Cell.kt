package com.example.minesweeper

class Cell constructor(private val value: Int) {
    companion object {
        const val BOMB: Int = -1
        const val BLANK: Int = 0
    }

    private var revealed: Boolean = false
    private var flagged: Boolean = false
    private var gameWon: Boolean = false

    public fun getValue(): Int {
        return this.value
    }

    public fun isRevealed(): Boolean {
        return this.revealed
    }

    public fun setRevealed(revealed: Boolean) {
        this.revealed = revealed
    }

    public fun isFlagged(): Boolean {
        return this.flagged
    }

    public fun setFlagged(flagged: Boolean) {
        this.flagged = flagged
    }

    public fun gameWon(): Boolean {
        return this.gameWon
    }

    public fun setGameWon(gameWon: Boolean) {
        this.gameWon = gameWon
    }
}