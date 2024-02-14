package com.tictactoe.game.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class GameViewModel
    constructor(boardSize: Int)
    : ViewModel(){

    private var _state by mutableStateOf(GameState(GameBoard(boardSize)))
    val state get() = this._state

    fun placeFigure (x: Int, y: Int) : Boolean {
        val board = this._state.board
        if (board[x][y] == Figure.EMPTY) {
            board[x][y] = this._state.nextPlayerValue
            val result = this.check().result

            val score = this._state.score
            val gameFinished = (result != Result.NONE)
            val nextPlayer = this._state.nextPlayerValue

            if (gameFinished)
                score[result] = score[result]?.plus(1)!!

            this._state = this._state.copy(
                _board = board,
                _score = score,
                _nextPlayer = nextPlayer.next(),
                _gameFinished = gameFinished
            )
            return true
        }

        return false
    }

    fun check (): Status {
        val board = this._state.board

        if (board.diagonal().all { it == Figure.O })
            return Status(Result.O, VictoryType.DIAGONAL_RIGHT)

        if (board.diagonal().all { it == Figure.X })
            return Status(Result.X, VictoryType.DIAGONAL_RIGHT)

        if (board.diagonal(false).all { it == Figure.O })
            return Status(Result.O, VictoryType.DIAGONAL_LEFT)

        if (board.diagonal(false).all { it == Figure.X })
            return Status(Result.X, VictoryType.DIAGONAL_LEFT)

        for (i: Int in 0 until board.size()) {
            if (board.row(i).all { it == Figure.O })
                return Status(Result.O, VictoryType.HORIZONTAL, index = i)

            if (board.row(i).all { it == Figure.X })
                return Status(Result.X, VictoryType.HORIZONTAL, index = i)

            if (board.column(i).all { it == Figure.O })
                return Status(Result.O, VictoryType.VERTICAL, index = i)

            if (board.column(i).all { it == Figure.X })
                return Status(Result.X, VictoryType.VERTICAL, index = i)
        }

        if (board.isFull())
            return Status(Result.TIE, VictoryType.NONE)

        return Status(Result.NONE, VictoryType.NONE)
    }

    fun reset() {
        val board = this._state.board
        board.clear()
        this._state = this._state.copy(
            _board = board,
            _score = this._state.score,
            _nextPlayer = Figure.O,
            _gameFinished = false
        )
    }
}