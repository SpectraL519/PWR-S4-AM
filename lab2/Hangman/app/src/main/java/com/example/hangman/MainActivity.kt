package com.example.hangman

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private var word: String = ""
    private var guess: String = ""
    private val maxGuesses: Int = 7
    private var numGuesses: Int = 0

    private var hangmanImage: ImageView? = null
    private var wordView: TextView? = null
    private var buttonsLayout: TableLayout? = null
    private val imageMap: Map<Int, Int> = mapOf(
        0 to R.drawable.hangman_0,
        1 to R.drawable.hangman_1,
        2 to R.drawable.hangman_2,
        3 to R.drawable.hangman_3,
        4 to R.drawable.hangman_4,
        5 to R.drawable.hangman_5,
        6 to R.drawable.hangman_6,
        7 to R.drawable.hangman_7,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initView()
        this.newGame()
    }

    private fun initView() {
        this.hangmanImage = findViewById<ImageView>(R.id.hangman_image)
        this.wordView = findViewById<TextView>(R.id.word)
        this.buttonsLayout = findViewById<TableLayout>(R.id.buttons_layout)
    }

    private fun newGame() {
        val words = resources.getStringArray(R.array.words)
        this.word = words[Random.nextInt(words.size)].toString().uppercase()
        this.guess = this.encodeWord(this.word)

        this.wordView?.setText(this.split(guess))
        this.wordView?.setTextColor(Color.WHITE)
        // TODO: reset hangman image
        this.enableLetterButtons(true)
        this.numGuesses = 0
        this.hangmanImage?.setBackgroundResource(this.imageMap[this.numGuesses]!!)
    }

    private fun enableLetterButtons (enable: Boolean) {
        val numRows: Int = this.buttonsLayout?.childCount!! - 1
        for (rowIdx: Int in 0..numRows) {
            val row: TableRow = this.buttonsLayout?.getChildAt(rowIdx) as TableRow
            val numButtons: Int = row.childCount - 1
            for (buttonIdx: Int in 0..numButtons) {
                val button: Button = row.getChildAt(buttonIdx) as Button
                this.enableButton(button, enable)
            }
        }
    }

    private fun enableButton (button: Button, enable: Boolean) {
        button.isEnabled = enable
        button.isClickable = enable

        if (enable) {
            button.setBackgroundColor(Color.BLACK)
        }
        else {
            button.setBackgroundColor(Color.GRAY)
        }
    }

    private fun revealLetter (letter: Char) {
        var guessCharArray: CharArray = this.guess.toCharArray()
        val length: Int = guessCharArray.size - 1
        for (index: Int in 0..length) {
            if (this.word[index] == letter) {
                guessCharArray[index] = letter
            }
        }
        this.guess = String(guessCharArray)
        this.wordView?.setText(this.split(this.guess))
    }

    fun checkLetter(view: View) {
        val button: Button = view as Button
        val letter: Char = button.getText().first()
        this.enableButton(button, false)

        if (this.word.contains(letter)) {
            this.revealLetter(letter)
            if (this.guess == this.word) {
                this.wordView?.setText(this.word)
                this.wordView?.setTextColor(Color.GREEN)
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                this.enableLetterButtons(false)
            }
        }
        else {
            this.nextStage()
            Toast.makeText(this,
                "Remaining guesses: " + (this.maxGuesses - this.numGuesses).toString(),
                Toast.LENGTH_SHORT).show()
            if (this.numGuesses == this.maxGuesses) {
                this.wordView?.setText(this.word)
                this.wordView?.setTextColor(Color.RED)
                Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show()
                this.enableLetterButtons(false)
            }
        }
    }

    private fun nextStage () {
        this.numGuesses++
        this.hangmanImage?.setBackgroundResource(this.imageMap[this.numGuesses]!!)
    }

    fun restartGame(view: View) {
        this.newGame()
    }

    private fun encodeWord(word: String): String {
        var encodedCharArray: CharArray = word.toCharArray()
        val length: Int = encodedCharArray.size - 1
        for (index: Int in 0..length) {
            if (word.elementAt(index).isLetter()) {
                encodedCharArray[index] = '_'
            }
        }
        return String(encodedCharArray)
    }

    private fun split (string: String): String {
        return string.chunked(1).joinToString(separator = " ")
    }
}