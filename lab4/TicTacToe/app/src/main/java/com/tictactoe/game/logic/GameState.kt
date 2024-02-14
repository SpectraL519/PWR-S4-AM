package com.tictactoe.game.logic


data class GameState
    constructor(
        private val _board: GameBoard,
        private val _score: MutableMap<Result, Int> = mutableMapOf(
            Result.O to 0,
            Result.X to 0,
            Result.TIE to 0
        ),
        private var _nextPlayer: Figure = Figure.O,
        private var _gameFinished: Boolean = false
    ) {

    val board get() = this._board
    val boardSize get() = this._board.size()
    fun getFigure (x: Int, y: Int): Figure = this._board[x][y]

    val score get() = this._score
    fun getScore (result: Result): Int =
        this._score.getOrDefault(result, 0)

    val nextPlayerValue get() = this._nextPlayer
    val gameFinished get() = this._gameFinished
}