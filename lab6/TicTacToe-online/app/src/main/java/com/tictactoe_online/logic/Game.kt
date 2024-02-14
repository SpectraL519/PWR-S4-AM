package com.tictactoe_online.logic

import com.tictactoe_online.activity.GameActivity
import com.tictactoe_online.logic.utils.*


class Game
    constructor(
        private val context: GameActivity,
        private val _boardSize: Int
    ) {

    private var _state: GameState = GameState(GameBoard(this._boardSize))
    val state: GameState
        get() = this._state


    fun placeFigure (x: Int, y: Int) : Boolean {
        if (this._state.gameFinished)
            return false

        val board = this._state.board
        if (board[x][y] == Figure.EMPTY) {
            board[x][y] = this._state.currentPlayer

            val result = this.checkStatus().result
            val finished = (result != Result.NONE)

            this._state.update(
                board = board,
                currentPlayer = this._state.currentPlayer.next(),
                finished = finished,
            )

            if (finished)
                this.context.showWinMessage(result)

            return true
        }

        return false
    }

    fun checkStatus() : Status {
        val board = this._state.board

        if (board.diagonal().all { it == Figure.O })
            return Status(
                result = Result.O,
                coordinates = List(this._boardSize) {
                        index -> Coordinates(index, index)
                }
            )

        if (board.diagonal().all { it == Figure.X })
            return Status(
                result = Result.X,
                coordinates = List(this._boardSize) {
                        index -> Coordinates(index, index)
                }
            )

        if (board.diagonal(false).all { it == Figure.O })
            return Status(
                result = Result.O,
                coordinates = List(this._boardSize) {
                        index -> Coordinates(index, this._boardSize - 1 - index)
                }
            )

        if (board.diagonal(false).all { it == Figure.X })
            return Status(
                result = Result.X,
                coordinates = List(this._boardSize) {
                        index -> Coordinates(index, this._boardSize - 1 - index)
                }
            )

        for (i: Int in 0 until this._boardSize) {
            if (board.row(i).all { it == Figure.O })
                return Status(
                    result = Result.O,
                    coordinates = List(this._boardSize) {
                            index -> Coordinates(i, index)
                    }
                )

            if (board.row(i).all { it == Figure.X })
                return Status(
                    result = Result.X,
                    coordinates = List(this._boardSize) {
                            index -> Coordinates(i, index)
                    }
                )

            if (board.column(i).all { it == Figure.O })
                return Status(
                    result = Result.O,
                    coordinates = List(this._boardSize) {
                            index -> Coordinates(index, i)
                    }
                )

            if (board.column(i).all { it == Figure.X })
                return Status(
                    result = Result.X,
                    coordinates = List(this._boardSize) {
                            index -> Coordinates(index, i)
                    }
                )
        }

        if (board.full())
            return Status(result = Result.TIE)

        return Status(result = Result.NONE)
    }

    fun nextPointAction() : List<Coordinates>? {
        this.reset()
        return null
    }

    fun reset() {
        val board = this._state.board
        board.clear()

        this._state.update(
            board = board,
            currentPlayer = Figure.O,
            finished = false
        )
    }

    enum class Result {
        NONE, TIE, X, O
    }
}