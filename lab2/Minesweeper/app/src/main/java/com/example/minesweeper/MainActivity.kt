package com.example.minesweeper

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), CellListener {
    private var grid: RecyclerView? = null
    private var smiley: TextView? = null
    private var timer: TextView? = null
    private var countDownTimer: CountDownTimer? = null
    private var flagButton: ImageButton? = null
    private var flagsLeft: TextView? = null

    private var game: MinesweeperGame? = null
    private var cellGridAdapter: CellGridAdapter? = null
    private var secondsElapsed = 0
    private var timerStarted = false

    companion object {
        const val TIMER_LENGTH = 999000L // 999 seconds in milliseconds
        const val BOMB_COUNT = 10
        const val GRID_SIZE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initModel()
        this.initView()
    }

    private fun initModel() {
        this.game = MinesweeperGame(GRID_SIZE, BOMB_COUNT)
        this.cellGridAdapter = CellGridAdapter(this.game?.getCellGrid()?.getCells()!!, this)
        this.secondsElapsed = 0
        this.timerStarted = false
    }

    @SuppressLint("DefaultLocale")
    private fun initView() {
        this.grid = findViewById<RecyclerView>(R.id.activity_main_grid)
        this.grid?.setLayoutManager(GridLayoutManager(this, 10))
        this.grid?.setAdapter(this.cellGridAdapter)

        this.flagsLeft = findViewById<TextView>(R.id.flags_left)
        this.flagButton = findViewById<ImageButton>(R.id.flag_button)
        this.flagsLeft?.setText(java.lang.String.format("%03d", this.game?.getNumBombs()!! - this.game?.getFlagCount()!!))
        this.flagButton?.setBackgroundColor(Color.TRANSPARENT)

        this.smiley = findViewById<TextView>(R.id.activity_main_smiley)
        this.smiley?.setOnClickListener(View.OnClickListener {
            this.game = MinesweeperGame(GRID_SIZE, BOMB_COUNT)
            this.cellGridAdapter?.setCells(game?.getCellGrid()?.getCells()!!)
            this.timerStarted = false
            this.countDownTimer?.cancel()
            this.secondsElapsed = 0
            this.timer?.setText(R.string.default_count)
            this.flagsLeft?.setText(java.lang.String.format("%03d", this.game?.getNumBombs()!! - this.game?.getFlagCount()!!))
        })

        this.timer = findViewById<TextView>(R.id.activity_main_timer)
        countDownTimer = object : CountDownTimer(TIMER_LENGTH, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsElapsed += 1
                timer?.setText(String.format("%03d", secondsElapsed))
            }

            override fun onFinish() {
                game?.outOfTime()
                Toast.makeText(applicationContext, "Game Over: Timer Expired", Toast.LENGTH_SHORT)
                    .show()
                game?.getCellGrid()?.revealBombs(false)
                cellGridAdapter?.setCells(game?.getCellGrid()?.getCells()!!)
            }
        }
    }

    fun toggleFlag(view: View) {
        this.game?.toggleMode()
        if (this.game?.inFlagMode()!!) {
            this.flagButton?.setBackgroundColor(Color.GREEN)
        }
        else {
            this.flagButton?.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    @SuppressLint("DefaultLocale")
   override fun cellClick (cell: Cell?) {
        this.game?.handleCellClick(cell!!)
        this.flagsLeft?.text = java.lang.String.format(
            "%03d",
            this.game?.getNumBombs()!! - this.game?.getFlagCount()!!
        )

        if (!this.timerStarted) {
            this.countDownTimer?.start()
            this.timerStarted = true
        }
        if (this.game?.gameOver()!!) {
            this.countDownTimer?.cancel()
            Toast.makeText(applicationContext, "Game Over", Toast.LENGTH_SHORT).show()
            this.game?.getCellGrid()?.revealBombs(false)
        }
        if (this.game?.gameWon!!) {
            this.countDownTimer?.cancel()
            Toast.makeText(applicationContext, "Game Won", Toast.LENGTH_SHORT).show()
            this.game?.getCellGrid()?.revealBombs(true)
        }
        this.cellGridAdapter?.setCells(this.game?.getCellGrid()?.getCells()!!)
    }
}