package com.tictactoe_online.logic.utils

import com.tictactoe_online.logic.Game

data class Status
    constructor(
        val result: Game.Result = Game.Result.NONE,
        val coordinates: List<Coordinates> = emptyList()
    )
