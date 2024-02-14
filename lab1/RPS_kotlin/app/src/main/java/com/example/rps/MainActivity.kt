package com.example.rps

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // model
    private var playerScore: Int = 0
    private var botScore: Int = 0
    private val figures: Map<String, Int> = mapOf<String, Int>("Rock" to R.drawable.rock_button,
                                                               "Paper" to R.drawable.paper_button,
                                                               "Scissors" to R.drawable.scissors_button)
    private val rock: Figure = Figure("Rock", "Scissors")
    private val paper: Figure = Figure("Paper", "Rock")
    private val scissors: Figure = Figure("Scissors", "Paper")
    private var choice: Figure? = null

    // view
    private var rockB: ImageButton? = null
    private var paperB: ImageButton? = null
    private var scissorsB: ImageButton? = null
    private var scoreText: TextView? = null
    private var emptyFigure: ImageView? = null
    private var playersFigure: ImageView? = null
    private var botsFigure: ImageView? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initView()
    }

    private fun initView () {
        this.rockB = findViewById<ImageButton>(R.id.rockButton)
        this.paperB = findViewById<ImageButton>(R.id.paperButton)
        this.scissorsB = findViewById<ImageButton>(R.id.scissorsButton)
        this.scoreText = findViewById<TextView>(R.id.scoreText)
        this.playersFigure = findViewById<ImageView>(R.id.playersFigure)
        this.botsFigure = findViewById<ImageView>(R.id.botsFigure)

        this.rockB?.setBackgroundColor(Color.TRANSPARENT)
        this.paperB?.setBackgroundColor(Color.TRANSPARENT)
        this.scissorsB?.setBackgroundColor(Color.TRANSPARENT)
    }

    fun selectRock(view: View) {
        // Toast.makeText(this, "Rock selected!", Toast.LENGTH_SHORT).show()
        this.choice = rock
        this.rockB?.setBackgroundColor(Color.GREEN)

        this.paperB?.setBackgroundColor(Color.TRANSPARENT)
        this.scissorsB?.setBackgroundColor(Color.TRANSPARENT)
    }

    fun selectPaper(view: View) {
        // Toast.makeText(this, "Paper selected!", Toast.LENGTH_SHORT).show()
        this.choice = paper
        this.paperB?.setBackgroundColor(Color.GREEN)

        this.rockB?.setBackgroundColor(Color.TRANSPARENT)
        this.scissorsB?.setBackgroundColor(Color.TRANSPARENT)
    }

    fun selectScissors(view: View) {
        // Toast.makeText(this, "Scissors selected!", Toast.LENGTH_SHORT).show()
        this.choice = scissors
        this.scissorsB?.setBackgroundColor(Color.GREEN)

        this.rockB?.setBackgroundColor(Color.TRANSPARENT)
        this.paperB?.setBackgroundColor(Color.TRANSPARENT)
    }

    @SuppressLint("SetTextI18n")
    fun submitChoice(view: View) {
        if (this.choice == null) {
            Toast.makeText(this, "Choose figure before submitting!", Toast.LENGTH_SHORT).show()
            return
        }

        this.rockB?.setBackgroundColor(Color.TRANSPARENT)
        this.paperB?.setBackgroundColor(Color.TRANSPARENT)
        this.scissorsB?.setBackgroundColor(Color.TRANSPARENT)

        val playersFigureImage = this.figures[this.choice?.getValue()]!!
        this.playersFigure?.setBackgroundResource(playersFigureImage)

        val randFigure = this.figures.entries.elementAt(Random.nextInt(3))
        val botsFigureImage = randFigure.value
        this.botsFigure?.setBackgroundResource(botsFigureImage)

        val hierarchy = this.choice?.hierarchy(randFigure.key)
        // Log.i("Hierarchy", hierarchy.toString())
        if (hierarchy == 1)
            this.playerScore = this.playerScore.inc()
        else if (hierarchy == -1)
            this.botScore = this.botScore.inc()

        this.scoreText?.text = this.playerScore.toString() + ":" + this.botScore.toString()
        if (this.playerScore > this.botScore)
            this.scoreText?.setTextColor(Color.GREEN)
        else if (this.playerScore < this.botScore)
            this.scoreText?.setTextColor(Color.RED)
        else
            this.scoreText?.setTextColor(Color.WHITE)

        this.choice = null
    }

    @SuppressLint("SetTextI18n")
    fun restartGame(view: View) {
        this.playerScore = 0
        this.botScore = 0
        this.choice = null

        this.scoreText?.text = this.playerScore.toString() + ":" + this.botScore.toString()
        this.scoreText?.setTextColor(Color.WHITE)

        this.playersFigure?.setBackgroundResource(0)
        this.botsFigure?.setBackgroundResource(0)

        this.rockB?.setBackgroundColor(Color.TRANSPARENT)
        this.paperB?.setBackgroundColor(Color.TRANSPARENT)
        this.scissorsB?.setBackgroundColor(Color.TRANSPARENT)

        Toast.makeText(this, "New game started!", Toast.LENGTH_SHORT).show()
    }
}