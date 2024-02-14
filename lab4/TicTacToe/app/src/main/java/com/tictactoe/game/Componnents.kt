package com.tictactoe.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.tictactoe.ui.theme.Aqua
import com.tictactoe.ui.theme.GreenishYellow

@Composable
fun DrawBoard (boardSize: Int) {
    Canvas(
        modifier = Modifier.size(300.dp)
    ) {
        for (i in 1 until boardSize) {
            drawLine(
                color = Color.Gray,
                strokeWidth = 5f,
                cap = StrokeCap.Round,
                start = Offset(x = size.width * i / boardSize, y = 0f),
                end = Offset(x = size.width * i / boardSize, y = size.height)
            )
            drawLine(
                color = Color.Gray,
                strokeWidth = 5f,
                cap = StrokeCap.Round,
                start = Offset(x = 0f, y = size.height * i / boardSize),
                end = Offset(x = size.width, y = size.height * i / boardSize)
            )
        }
    }
}


@Composable
fun DrawO () {
    Canvas(
        modifier = Modifier
            .size(60.dp)
            .padding(5.dp)
    ) {
        drawCircle(
            color = Aqua,
            style = Stroke(width = 10f)
        )
    }
}


@Composable
fun DrawX () {
    Canvas(
        modifier = Modifier
            .size(60.dp)
            .padding(5.dp)
    ) {
        drawLine(
            color = GreenishYellow,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height)
        )
        drawLine(
            color = GreenishYellow,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        )
    }
}


@Composable
fun DrawVerticalLine (boardSize: Int, column: Int) {
    Canvas(modifier = Modifier.size(300.dp)) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = size.width * (1 + 2 * column) / (2 * boardSize), y = 0f),
            end = Offset(x = size.width * (1 + 2 * column) / (2 * boardSize), y = size.height)
        )
    }
}


@Composable
fun DrawHorizontalLine (boardSize: Int, row: Int) {
    Canvas(modifier = Modifier.size(300.dp)) {
        drawLine(
            color = Color.Red,
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height * (1 + 2 * row) / (2 * boardSize)),
            end = Offset(x = size.width, y = size.height * (1 + 2 * row) / (2 * boardSize))
        )
    }
}


@Composable
fun DrawDiagonalLine (rightSkew: Boolean) {
    if (rightSkew) {
        Canvas(modifier = Modifier.size(300.dp)) {
            drawLine(
                color = Color.Red,
                strokeWidth = 10f,
                cap = StrokeCap.Round,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = size.width, y = size.height)
            )
        }
    }
    else {
        Canvas(modifier = Modifier.size(300.dp)) {
            drawLine(
                color = Color.Red,
                strokeWidth = 10f,
                cap = StrokeCap.Round,
                start = Offset(x = 0f, y = size.height),
                end = Offset(x = size.width, y = 0f)
            )
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun DrawPreviews () {
    val boardSize = 4
    DrawBoard(boardSize)
    for (i in 0 until boardSize) {
        DrawVerticalLine(boardSize = boardSize, column = i)
        DrawHorizontalLine(boardSize = boardSize, row = i)
    }
    DrawDiagonalLine(rightSkew = true)
    DrawDiagonalLine(rightSkew = false)
}
*/