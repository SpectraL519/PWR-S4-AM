package com.tictactoe

import android.content.Context
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tictactoe.main.MainScreen
import com.tictactoe.main.MainViewModel
import com.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                MainScreen(viewModel = MainViewModel())
            }
        }
    }
}