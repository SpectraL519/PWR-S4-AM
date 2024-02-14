package com.tictactoe.main

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tictactoe.game.GameActivity
import com.tictactoe.ui.theme.BlueCustom
import com.tictactoe.ui.theme.GrayBackground


@Composable
fun MainScreen (viewModel: MainViewModel) {
    val state = viewModel.state
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(GrayBackground)
            .padding(
                vertical = 30.dp,
                horizontal = 30.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            color = BlueCustom,
            modifier = Modifier.padding(bottom = 100.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayBackground)
                .padding(vertical = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Board size:",
                fontSize = 24.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            viewModel.updateBoardSize(increment = false)
                        }
                )
                Text(
                    text = state.boardSize.toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            viewModel.updateBoardSize(increment = true)
                        }
                )
            }

            Button(
                onClick = {
                    context.startActivity(
                        Intent(context, GameActivity::class.java).apply {
                            putExtra("board_size", state.boardSize)
                        }
                    )
                    /*
                    Toast.makeText(
                        context,
                        String.format("Starting game (board size: %d)", state.boardSize),
                        Toast.LENGTH_SHORT
                    ).show()
                    */
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueCustom,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(top = 100.dp)
            ) {
                Text(
                    text = "START GAME",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview () {
    MainScreen(
        viewModel = MainViewModel()
    )
}