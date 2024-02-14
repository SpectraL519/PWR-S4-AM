package com.tictactoe.game

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tictactoe.game.logic.*
import com.tictactoe.ui.*
import com.tictactoe.ui.theme.BlueCustom
import com.tictactoe.ui.theme.GrayBackground

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen (viewModel: GameViewModel) {
    val state = viewModel.state
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackground)
            .padding(
                vertical = 30.dp,
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
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = String.format("Player O: %d", state.getScore(Result.O)),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = String.format("Tie: %d", state.getScore(Result.TIE)),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = String.format("Player X: %d", state.getScore(Result.X)),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .background(GrayBackground),
                contentAlignment = Alignment.Center
            ) {
                DrawBoard(state.boardSize)
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1f),
                    columns = GridCells.Fixed(state.boardSize)
                ) {
                    for (row: Int in 0 until state.boardSize) {
                        for (column: Int in 0 until state.boardSize) {
                            val figure: Figure = state.getFigure(row, column)
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .clickable(
                                            interactionSource = MutableInteractionSource(),
                                            indication = null
                                        ) { viewModel.placeFigure(row, column) },
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    AnimatedVisibility(
                                        visible = figure != Figure.EMPTY,
                                        enter = scaleIn(tween(1000))
                                    ) {
                                        if (figure == Figure.O)
                                            DrawO()
                                        else if (figure == Figure.X)
                                            DrawX()
                                    }
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AnimatedVisibility(
                        visible = state.gameFinished,
                        enter = fadeIn(tween(2000))
                    ) {
                        val status = viewModel.check()
                        DrawVictoryLine(state = state, status = status)
                        if (status.result != Result.NONE)
                            Toast.makeText(context, status.result.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = String.format("Turn: %s", state.nextPlayerValue.toString()),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace
                )
                Button(
                    onClick = {
                        if (state.gameFinished)
                            viewModel.reset()
                    },
                    shape = RoundedCornerShape(5.dp),
                    elevation = ButtonDefaults.elevation(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = BlueCustom,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Play Again",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}


@Composable
fun DrawVictoryLine(state: GameState, status: Status) {
    when (status.victoryType) {
        VictoryType.HORIZONTAL ->
            DrawHorizontalLine(
                boardSize = state.boardSize,
                row = status.index)

        VictoryType.VERTICAL ->
            DrawVerticalLine(
                boardSize = state.boardSize,
                column = status.index
            )

        VictoryType.DIAGONAL_RIGHT -> DrawDiagonalLine(rightSkew = true)
        VictoryType.DIAGONAL_LEFT -> DrawDiagonalLine(rightSkew = false)

        VictoryType.NONE -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview (boardSize: Int = 3) {
    GameScreen(GameViewModel(boardSize))
}