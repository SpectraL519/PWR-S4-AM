package com.tictactoe.game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tictactoe.game.logic.GameViewModel
import com.tictactoe.ui.theme.TicTacToeTheme


class GameActivity : ComponentActivity() {
    private var boardSize: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.boardSize = intent.getIntExtra("board_size", 3)
        setContent {
            TicTacToeTheme {
                GameScreen(viewModel = GameViewModel(this.boardSize))
            }
        }
    }
}