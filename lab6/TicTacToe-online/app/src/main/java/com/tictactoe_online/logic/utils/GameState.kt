package com.tictactoe_online.logic.utils

import com.tictactoe_online.logic.Game


data class GameState
    constructor(
        private var _board: GameBoard,
        private var _currentPlayer: Figure = Figure.O,
        private var _finished: Boolean = false
    ) {

    val board get() = this._board
    val boardSize get() = this._board.size()
    fun getFigure (x: Int, y: Int): Figure = this._board[x][y]

    val currentPlayer get() = this._currentPlayer
    val gameFinished get() = this._finished

    fun update (
        board: GameBoard = this._board,
        currentPlayer: Figure = this._currentPlayer,
        finished: Boolean = this._finished,
    ) {
        this._board = board
        this._currentPlayer = currentPlayer
        this._finished = finished
    }
}
