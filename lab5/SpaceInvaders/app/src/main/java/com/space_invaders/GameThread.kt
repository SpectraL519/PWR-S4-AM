package com.space_invaders


class GameThread (private val gameView: GameView) : Thread() {

    var running = false
    val targetFps: Long = 30
    private val targetTime = (1000 / this.targetFps)

    override fun run() {
        var startTime: Long
        var execTime: Long
        var waitTime: Long

        while (this.running) {
            startTime = System.nanoTime()

            synchronized(this) {
                if (!this.gameView.gamePaused) {
                    this.gameView.update()

                    this.gameView.draw()
                }
            }

            execTime = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - execTime
            if (waitTime > 0)
                sleep(waitTime)
        }
    }
}