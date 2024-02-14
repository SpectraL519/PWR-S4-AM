package com.tictactoe_online.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tictactoe_online.R


class MainActivity : AppCompatActivity() {
    private lateinit var startGameCV: CardView
    private lateinit var accountTV: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initView()
    }

    private fun initView() {
        this.accountTV = findViewById(R.id.account_tv)
        this.setAccountTVText()
        this.accountTV.setOnClickListener {
            if (Firebase.auth.currentUser == null) {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            }
            else {
                Firebase.auth.signOut()
                this.accountTV.text = getString(R.string.login)
                Toast.makeText(this, "You've been signed out", Toast.LENGTH_LONG).show()
            }
        }

        this.startGameCV = findViewById(R.id.start_game_cv)
        this.startGameCV.setOnClickListener {
            if (Firebase.auth.currentUser == null)
                Toast.makeText(this, "Log in to play!", Toast.LENGTH_SHORT).show()
            else
                startActivity(Intent(this, GameActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        this.setAccountTVText()
    }

    override fun onResume() {
        super.onResume()
        this.setAccountTVText()
    }

    private fun setAccountTVText() {
        this.accountTV.text = when (Firebase.auth.currentUser) {
            null -> getString(R.string.login)
            else -> "${getString(R.string.sign_out)} (${Firebase.auth.currentUser!!.email!!.subSequence(0, 5)})"
        }
    }
}
