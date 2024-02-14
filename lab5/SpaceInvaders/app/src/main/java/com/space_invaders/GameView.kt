package com.space_invaders

import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.room.Room
import com.space_invaders.database.AppDatabase
import com.space_invaders.database.Score
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
class GameView (private val context: MainActivity)
    : SurfaceView(context), SurfaceHolder.Callback {

    // general
    private val soundPlayer = SoundPlayer(this.context)
    private val gameThread = GameThread(this)

    // app data
    private val db = Room.databaseBuilder(
        this.context,
        AppDatabase::class.java,
        "score-db"
    ).build()
    private val scoreDao = this.db.scoreDao()
    private lateinit var highScore: Score

    private lateinit var size: Size


    init {
        this.holder.addCallback(this)
        GlobalScope.launch {
            if (!this@GameView.scoreDao.idExists(0))
                this@GameView.scoreDao.insert(Score(0, 0))
            this@GameView.highScore = this@GameView.scoreDao.getHighScore()
        }
    }

    var gamePaused = true

    private val paint: Paint = Paint().apply { color = Color.argb(255, 0, 255, 0) }
    private val statsPaint: Paint = Paint().apply {
        textSize = 50f
        color = Color.argb(255, 255, 255, 255)
    }

    // game elements
    private lateinit var spaceFighter: SpaceFighter
    private lateinit var fighterBullet: Bullet
    private val shieldPieces = ArrayList<ShieldPiece>()
    private var numShieldPieces = 0

    private val invaders = ArrayList<Invader>()
    private val invaderBullets = ArrayList<Bullet>()
    private var numInvaderBullets = 0
    private val maxInvaderBullets = 10

    // statistics
    private var score = 0
    private var wave = 1
    private var lives = 3

    override fun surfaceCreated (holder: SurfaceHolder) {
        val canvas = this.holder.lockCanvas()
        this.size = Size(canvas.width, canvas.height)
        this.holder.unlockCanvasAndPost(canvas)

        this.spaceFighter = SpaceFighter(this.context, this.size)
        this.fighterBullet = Bullet(this.size, 40f, 1200f)
        this.initGame()

        this.draw()
        this.gameThread.running = true
        this.gameThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed (holder: SurfaceHolder) {
        this.gameThread.running = false

        try {
            this.gameThread.join()
        }
        catch (e: InterruptedException) {
            Log.e("error", "Cannot join the game thread!")
        }

        GlobalScope.launch {
            val currentHighScore = this@GameView.scoreDao.getHighScore()
            if (this@GameView.highScore.score > currentHighScore.score)
                this@GameView.scoreDao.update(this@GameView.highScore)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val motionArea = this.size.height - (this.size.height / 4)
        val eventAction = event?.action?.and(MotionEvent.ACTION_MASK)

        if (eventAction == MotionEvent.ACTION_DOWN) {
            this.gamePaused = false

            if (event.y > motionArea) {
                if (event.x > this.size.width / 2)
                    this.spaceFighter.move(Direction.RIGHT, this.gameThread.targetFps)
                else
                    this.spaceFighter.move(Direction.LEFT, this.gameThread.targetFps)
            }

            if (
                event.y < motionArea &&
                this.fighterBullet.shoot(
                    this.spaceFighter.position.left + this.spaceFighter.width / 2f,
                    this.spaceFighter.position.top,
                    Direction.UP
                )
            ) { this.soundPlayer.play(SoundPlayer.shot) }
        }

        return super.onTouchEvent(event)
    }

    fun draw() {
        val canvas = this.holder.lockCanvas()

        // background
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        // canvas.drawColor(Color.argb(255, 0, 0, 0))

        // space fighter
        canvas.drawBitmap(
            this.spaceFighter.bitmap,
            this.spaceFighter.position.left,
            this.spaceFighter.position.top,
            null
        )

        // space fighter's bullet
        if (this.fighterBullet.active)
            canvas.drawRect(this.fighterBullet.position, this.paint)

        // shield pieces
        for (shieldPiece in this.shieldPieces)
            if (shieldPiece.undamaged)
                canvas.drawRect(shieldPiece.position, this.paint)

        // invaders
        for (invader in this.invaders)
            if (invader.alive)
                canvas.drawBitmap(
                    Invader.bitmapA,
                    invader.position.left,
                    invader.position.top,
                    null
                )

        // invaders' bullets
        for (bullet in this.invaderBullets)
            if (bullet.active)
                canvas.drawRect(bullet.position, this.paint)

        // statistics
        canvas.drawText(
            "Lives: ${this.lives} Wave: ${this.wave}\tScore: ${this.score}\tHS: ${this.highScore.score}",
            20f, 75f,
            this.statsPaint
        )

        this.holder.unlockCanvasAndPost(canvas)
    }

    fun update() {
        // this.spaceFighter.update(this.gameThread.targetFps)

        var invadersAtEdge = false
        var gameLost = false
        var waveCleared = false

        // update all alive invaders
        for (invader in this.invaders) {
            if (invader.alive) {
                invader.update(this.gameThread.targetFps)

                if (
                    invader.aim(
                        this.spaceFighter.position.left,
                        this.spaceFighter.width,
                        this.wave
                    )
                ) {
                    if (
                        this.invaderBullets[this.numInvaderBullets].shoot(
                            invader.position.left + invader.width / 2,
                            invader.position.top,
                            Direction.DOWN
                        )
                    ) {
                        this.numInvaderBullets++
                        if (this.numInvaderBullets == this.maxInvaderBullets)
                            this.numInvaderBullets = 0
                    }
                }

                if (invader.position.left < 0 || invader.position.right > this.size.width)
                    invadersAtEdge = true
            }
        }

        // update space fighter's bullet
        if (this.fighterBullet.active)
            this.fighterBullet.update(this.gameThread.targetFps)

        // update all invaders' bullets
        for (bullet in this.invaderBullets)
            if (bullet.active)
                bullet.update(this.gameThread.targetFps)

        // move invaders forward if an invader is at an edge of the screen
        if (invadersAtEdge)
            for (invader in this.invaders) {
                invader.moveForwardAndReverse(this.wave)
                if (invader.alive && invader.position.bottom >= this.size.height)
                    gameLost = true
            }

        // deactivate space fighter' bullet
        if (this.fighterBullet.position.bottom < 0)
            this.fighterBullet.active = false

        // deactivate invaders' bullets
        for (bullet in this.invaderBullets)
            if (bullet.position.top > this.size.height)
                bullet.active = false

        // space fighter -> invader hit
        if (this.fighterBullet.active)
            for (invader in this.invaders)
                if (
                    invader.alive &&
                    RectF.intersects(this.fighterBullet.position, invader.position)
                ) {
                    this.soundPlayer.play(SoundPlayer.invaderDown)
                    invader.alive = false
                    Invader.aliveInvaders--
                    this.fighterBullet.active = false

                    this.score++
                    if (this.score > this.highScore.score)
                        this.highScore.score = this.score

                    if (Invader.aliveInvaders == 0) {
                        waveCleared = true
                        break
                    }

                    break
                }

        // space fighter -> shield piece hit
        if (this.fighterBullet.active)
            for (shieldPiece in this.shieldPieces)
                if (
                    shieldPiece.undamaged &&
                    RectF.intersects(this.fighterBullet.position, shieldPiece.position)
                ) {
                    this.fighterBullet.active = false
                    shieldPiece.undamaged = false
                    this.soundPlayer.play(SoundPlayer.shieldDamage)
                }

        // invader -> shield piece hit
        for (bullet in this.invaderBullets)
            if (bullet.active)
                for (shieldPiece in this.shieldPieces)
                    if (
                        shieldPiece.undamaged &&
                        RectF.intersects(bullet.position, shieldPiece.position)
                    ) {
                        bullet.active = false
                        shieldPiece.undamaged = false
                        this.soundPlayer.play(SoundPlayer.shieldDamage)
                    }

        // invader -> space fighter hit
        for (bullet in this.invaderBullets)
            if (
                bullet.active &&
                RectF.intersects(bullet.position, this.spaceFighter.position)
            ) {
                bullet.active = false
                this.lives--
                this.soundPlayer.play(SoundPlayer.playerDown)

                if (this.lives == 0) {
                    gameLost = true
                    break
                }
            }

        if (gameLost) {
            this.context.runOnUiThread(Runnable {
                Toast.makeText(this.context, "Game lost!", Toast.LENGTH_SHORT).show()
            })

            this.gamePaused = true
            this.lives = 3
            this.wave = 1
            this.score = 0

            this.invaders.clear()
            this.shieldPieces.clear()
            this.invaderBullets.clear()
            this.initGame()
        }

        if (waveCleared) {
            this.context.runOnUiThread(Runnable {
                Toast.makeText(this.context, "Wave cleared!", Toast.LENGTH_SHORT).show()
            })

            this.gamePaused = true
            this.lives++
            this.wave++

            this.invaders.clear()
            this.shieldPieces.clear()
            this.invaderBullets.clear()
            this.initGame()
        }
    }

    private fun initGame() {
        Invader.aliveInvaders = 0
        for (row in 0..5) {
            for (column in 0..10) {
                this.invaders.add(
                    Invader(this.context, row, column, this.size)
                )
                Invader.aliveInvaders++
            }
        }

        for (i in 0 until this.maxInvaderBullets)
            this.invaderBullets.add(Bullet(this.size))


        this.numShieldPieces = 0
        for (shieldIdx in 0..ShieldPiece.NUM_SHIELDS) {
            for (row in 0..ShieldPiece.NUM_ROWS) {
                for (column in 0..ShieldPiece.NUM_COLUMNS) {
                    this.shieldPieces.add(
                        ShieldPiece(row, column, shieldIdx, this.size)
                    )
                    this.numShieldPieces++
                }
            }
        }
    }
}