package com.example.w01a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var n: Int = 0
    private var m: Int = 0
    private var score: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.score = 0
        this.roll()
    }

    fun roll () {
        findViewById<TextView>(R.id.points).text = "Punkty: " + this.score
        this.n = Random.nextInt(10)
        this.m = Random.nextInt(10)
        findViewById<TextView>(R.id.leftButton).text = "" + this.n
        findViewById<TextView>(R.id.rightButton).text = "" + this.m
    }

    fun leftClick(view: View) {
        if (this.n >= this.m) {
            this.score++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        }
        else {
            this.score--
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
        }

        this.roll()
    }

    fun rightClick(view: View) {
        if (this.m >= this.n) {
            this.score++
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        }
        else {
            this.score--
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
        }

        this.roll()
    }
}