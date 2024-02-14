package com.tictactoe_online.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.tictactoe_online.R
import com.tictactoe_online.logic.Game
import com.tictactoe_online.logic.utils.Coordinates
import com.tictactoe_online.logic.utils.Figure



class GameActivity : AppCompatActivity() {

    private val size = 3
    private lateinit var accountTV: TextView
    private lateinit var gameBoardTL: TableLayout
    private lateinit var game: Game
    private lateinit var turnTV: TextView
    private lateinit var cells: Array<Array<ImageView>>

    private val playerName = Firebase.auth.currentUser!!.email!!
    private var databaseReference = FirebaseDatabase
        .getInstance()
        .getReferenceFromUrl("https://pwr-am-default-rtdb.firebaseio.com/")
    private var playerUniqueId = "0"
    private var opponentUniqueId = "0"
    private var opponentName = ""
    private var gameId = ""
    private var player = Figure.EMPTY
    private lateinit var movesEventListener: ValueEventListener
    private lateinit var opponentNameTV: TextView
    private lateinit var playingAsTV: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        this.game = Game(this, this.size)

        this.initView()
        this.initConnection()
    }

    private fun initView() {
        this.opponentNameTV = findViewById(R.id.opponent_name_tv)
        this.playingAsTV = findViewById(R.id.playing_as_tv)

        this.accountTV = findViewById(R.id.account_tv)
        this.setAccountTVText()
        this.accountTV.setOnClickListener {
            if (Firebase.auth.currentUser == null) {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            } else {
                Firebase.auth.signOut()
                this.accountTV.text = getString(R.string.login)
                Toast.makeText(this, "You've been signed out", Toast.LENGTH_LONG).show()
            }
        }

        this.turnTV = findViewById(R.id.turn_tv)
        this.turnTV.text = String.format("TURN: %s", this.game.state.currentPlayer.toString())

        this.gameBoardTL = findViewById(R.id.game_board_tl)
        this.gameBoardTL.removeAllViews()

        this.cells = Array(this.size) { Array(this.size) { ImageView(this) } }
        for (i in 0 until this.size) {
            val tableRow = TableRow(this)
            for (j in 0 until this.size) {
                val layoutParams = TableRow.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1f
                )
                layoutParams.setMargins(3, 3, 3, 3)
                this.cells[i][j].layoutParams = layoutParams
                this.cells[i][j].setBackgroundColor(Color.LTGRAY)
                this.cells[i][j].setImageResource(android.R.color.transparent)
                this.cells[i][j].scaleType = ImageView.ScaleType.FIT_XY
                this.cells[i][j].setOnClickListener { cellClick(cells[i][j], i, j) }
                tableRow.addView(this.cells[i][j])
            }
            val rowParams = TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            tableRow.layoutParams = rowParams
            this.gameBoardTL.addView(tableRow)
            this.gameBoardTL.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    gameBoardTL.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    gameBoardTL.height //height is ready
                }
            })
        }
    }

    private fun setAccountTVText() {
        this.accountTV.text = when (Firebase.auth.currentUser) {
            null -> getString(R.string.login)
            else -> "${getString(R.string.sign_out)} (${Firebase.auth.currentUser!!.email!!.subSequence(0, 5)})"
        }
    }

    private fun cellClick (imageView: ImageView, x: Int, y: Int) {
        if (this.game.state.currentPlayer == this.player) {
            if (this.game.placeFigure(x, y)) {
                val figure = this.game.state.getFigure(x, y)
                this.cells[x][y].setImageResource(figure.imageResource)

                this.checkDimensions()
                this.turnTV.text = String.format("TURN: %s", figure.next().toString())

                val status = this.game.checkStatus()
                if (status.result != Game.Result.NONE) {
                    if (status.result == Game.Result.O || status.result == Game.Result.X) {
                        for (c in status.coordinates) {
                            this.cells[c.row][c.column].setBackgroundColor(getColor(R.color.light_green))
                        }
                    }
                }
                this.databaseReference
                    .child("games")
                    .child(this.gameId)
                    .child("last_move")
                    .ref.setValue(Coordinates(x, y).toString())
            }
        }
        else
            Toast.makeText(this, "Waiting for opponent's move", Toast.LENGTH_SHORT).show()
    }

    private fun initConnection() {
        val progressDialog = ProgressDialog(this).apply {
            setMessage("Waiting for opponent")
            setCancelable(false)
            val colorDrawable = ColorDrawable(ContextCompat.getColor(this@GameActivity, R.color.bg_color))
            window?.setBackgroundDrawable(colorDrawable)
            show()
        }

        this.playerUniqueId = System.currentTimeMillis().toString()
        this.databaseReference.child("games").addValueEventListener(object: ValueEventListener {
            private var opponentFound = false
            private var createdGame = false

            private fun createGame (snapshot: DataSnapshot) {
                this.createdGame = true
                this@GameActivity.gameId = System.currentTimeMillis().toString()
                snapshot
                    .child(this@GameActivity.gameId)
                    .child(this@GameActivity.playerUniqueId)
                    .child("player_name")
                    .ref.setValue(this@GameActivity.playerName)
            }

            private fun addOpponent (gameId: String, connection: DataSnapshot) {
                if (this@GameActivity.gameId != gameId)
                    return

                this@GameActivity.player = Figure.O
                this@GameActivity.playingAsTV.text = "Playing as: O"

                for (player in connection.children) {
                    if (player.key != this@GameActivity.playerUniqueId) {
                        this.opponentFound = true
                        this@GameActivity.opponentName = player.child("player_name").value as String
                        this@GameActivity.opponentUniqueId = player.key.toString()

                        this@GameActivity.gameId = gameId
                        this@GameActivity.databaseReference
                            .child("games")
                            .child(this@GameActivity.gameId)
                            .child("last_move")
                            .addValueEventListener(this@GameActivity.movesEventListener)

                        progressDialog.hide()
                        this@GameActivity.opponentNameTV.text = String.format(
                            "Playing with %s",
                            this@GameActivity.opponentName
                        )
                        this@GameActivity.databaseReference
                            .child("games")
                            .removeEventListener(this)
                        break
                    }
                }
            }

            private fun joinGame(connId: String, game: DataSnapshot) {
                game
                    .child(this@GameActivity.playerUniqueId)
                    .child("player_name")
                    .ref.setValue(this@GameActivity.playerName)

                for (player in game.children) {
                    if (player.key != this@GameActivity.playerUniqueId) {
                        this.opponentFound = true
                        this@GameActivity.opponentName = player.child("player_name").value as String
                        this@GameActivity.opponentUniqueId = player.key.toString()
                        this@GameActivity.player = Figure.X
                        this@GameActivity.playingAsTV.text = "Playing as: X"
                        this@GameActivity.gameId = connId
                        this@GameActivity.databaseReference
                            .child("games")
                            .child(this@GameActivity.gameId)
                            .addValueEventListener(this@GameActivity.movesEventListener)
                        progressDialog.hide()
                        this@GameActivity.opponentNameTV.text = String.format(
                            "Playing with %s",
                            this@GameActivity.opponentName
                        )
                        this@GameActivity.databaseReference
                            .child("games")
                            .removeEventListener(this)
                        break
                    }
                }
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (this.opponentFound)
                    return

                if (snapshot.hasChildren()) {
                    for (game in snapshot.children) {
                        val gameId = game.key!!
                        val playersCount = game.childrenCount.toInt()

                        Log.d("am2023", gameId)
                        Log.d("am2023", playersCount.toString())

                        if (this.createdGame && playersCount == 2) {
                            this.addOpponent(gameId, game)
                        }
                        else if (!this.createdGame && playersCount == 1) {
                            this.joinGame(gameId, game)
                        }
                    }
                    if (!this.opponentFound && !this.createdGame) {
                        this.createGame(snapshot)
                    }
                }
                else if (!this.createdGame) {
                    this.createGame(snapshot)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        this.movesEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val c = Coordinates.fromString(snapshot.value.toString())
                if (c != null) {
                    this@GameActivity.game.placeFigure(c.row, c.column)

                    val figure = this@GameActivity.game.state.getFigure(c.row, c.column)
                    this@GameActivity.cells[c.row][c.column].setImageResource(figure.imageResource)
                    this@GameActivity.turnTV.text = String.format("TURN: %s", figure.next().toString())

                    this@GameActivity.checkDimensions()

                    val status = this@GameActivity.game.checkStatus()
                    if (status.result != Game.Result.NONE) {
                        if (status.result == Game.Result.O || status.result == Game.Result.X) {
                            for (c_stat in status.coordinates) {
                                this@GameActivity.cells[c_stat.row][c_stat.column]
                                    .setBackgroundColor(getColor(R.color.light_green))
                            }
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        }
    }

    fun checkDimensions () {
        if (this.cells[0][0].layoutParams.height == -1 && this.cells[0][0].width != 0){
            val w = this.cells[0][0].width
            for (i in 0 until this.size) {
                for (j in 0 until this.size) {
                    this.cells[i][j].layoutParams.height = w
                }
            }
        }
    }

    fun showWinMessage(result: Game.Result) {
        val message =
            if (result == Game.Result.TIE)
                result.toString()
            else
                "player $result won!"

        Toast.makeText(
            this,
            "Game Over: $message",
            Toast.LENGTH_SHORT
        ).show()
    }
}
