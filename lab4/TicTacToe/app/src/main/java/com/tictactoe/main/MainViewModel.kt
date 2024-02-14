package com.tictactoe.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


data class MainState (
    val boardSize: Int = 3
)

class MainViewModel : ViewModel() {
    private var _state by mutableStateOf(MainState())
    val state get() = this._state

    fun updateBoardSize (increment: Boolean) {
        if (increment) {
            this._state = this._state.copy(
                boardSize =
                    if (this._state.boardSize < 20)
                        this._state.boardSize + 1
                    else
                        this._state.boardSize
            )
        }
        else {
            this._state = this._state.copy(
                boardSize =
                    if (this._state.boardSize > 3)
                        this._state.boardSize - 1
                    else
                        this._state.boardSize
            )
        }
    }
}