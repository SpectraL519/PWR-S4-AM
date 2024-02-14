package com.space_invaders

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.gameView = GameView(this)
        setContentView(this.gameView)
    }
}