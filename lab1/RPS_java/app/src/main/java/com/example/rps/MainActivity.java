package com.example.rps;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int playerScore;
    private int botScore;

    private Map<String, Integer> figures;
    private Figure rock;
    private Figure paper;
    private Figure scissors;
    private Figure choice;

    private ImageButton rockB;
    private ImageButton paperB;
    private ImageButton scissorsB;
    private TextView scoreText;
    private ImageView playerFigure;
    private ImageView botFigure;

    private Random generator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initModel();
        this.initView();
    }

    private void initModel () {
        this.playerScore = 0;
        this.botScore = 0;

        this.figures = new HashMap<>();
        this.figures.put("Rock", R.drawable.rock_button);
        this.figures.put("Paper", R.drawable.paper_button);
        this.figures.put("Scissors", R.drawable.scissors_button);
        this.figures = Collections.unmodifiableMap(this.figures);

        this.rock = new Figure("Rock", "Scissors");
        this.paper = new Figure("Paper", "Rock");
        this.scissors = new Figure("Scissors", "Paper");
        this.choice = null;

        this.generator = new Random();
    }

    private void initView () {
        Log.i("Checkpoint", "Init view");
        this.rockB = findViewById(R.id.rockButton);
        this.paperB = findViewById(R.id.paperButton);
        this.scissorsB = findViewById(R.id.scissorsButton);
        this.scoreText = findViewById(R.id.scoreText);
        this.playerFigure = findViewById(R.id.playersFigure);
        this.botFigure = findViewById(R.id.botsFigure);

        this.rockB.setBackgroundColor(Color.TRANSPARENT);
        this.paperB.setBackgroundColor(Color.TRANSPARENT);
        this.scissorsB.setBackgroundColor(Color.TRANSPARENT);
    }


    public void selectRock(View view) {
        this.choice = this.rock;
        this.rockB.setBackgroundColor(Color.GREEN);

        this.paperB.setBackgroundColor(Color.TRANSPARENT);
        this.scissorsB.setBackgroundColor(Color.TRANSPARENT);
    }

    public void selectPaper(View view) {
        this.choice = this.paper;
        this.paperB.setBackgroundColor(Color.GREEN);

        this.rockB.setBackgroundColor(Color.TRANSPARENT);
        this.scissorsB.setBackgroundColor(Color.TRANSPARENT);
    }

    public void selectScissors(View view) {
        this.choice = this.scissors;
        this.scissorsB.setBackgroundColor(Color.GREEN);

        this.rockB.setBackgroundColor(Color.TRANSPARENT);
        this.paperB.setBackgroundColor(Color.TRANSPARENT);
    }

    public void submitChoice(View view) {
        if (this.choice == null) {
            Toast.makeText(this, "Choose figure before submitting!", Toast.LENGTH_SHORT).show();
            return;
        }

        this.rockB.setBackgroundColor(Color.TRANSPARENT);
        this.paperB.setBackgroundColor(Color.TRANSPARENT);
        this.scissorsB.setBackgroundColor(Color.TRANSPARENT);

        Integer playersFigureImage = this.figures.get(this.choice.getValue());
        this.playerFigure.setBackgroundResource(playersFigureImage);

        String randomKey = this.figures.keySet().toArray()[this.generator.nextInt(this.figures.keySet().toArray().length)].toString();
        Integer randomFigure = this.figures.get(randomKey);
        this.botFigure.setBackgroundResource(randomFigure);

        int hierarchy = this.choice.hierarchy(randomKey);
        if (hierarchy == 1)
            this.playerScore++;
        else if (hierarchy == -1)
            this.botScore++;

        this.scoreText.setText(this.playerScore + ":" + this.botScore);
        if (this.playerScore > this.botScore)
            this.scoreText.setTextColor(Color.GREEN);
        else if (this.playerScore < this.botScore)
            this.scoreText.setTextColor(Color.RED);
        else
            this.scoreText.setTextColor(Color.WHITE);

        this.choice = null;
    }

    public void restartGame(View view) {
        this.playerScore = 0;
        this.botScore = 0;
        this.choice = null;

        this.scoreText.setText(this.playerScore + ":" + this.botScore);
        this.scoreText.setTextColor(Color.WHITE);

        this.playerFigure.setBackgroundResource(0);
        this.botFigure.setBackgroundResource(0);

        this.rockB.setBackgroundColor(Color.TRANSPARENT);
        this.paperB.setBackgroundColor(Color.TRANSPARENT);
        this.scissorsB.setBackgroundColor(Color.TRANSPARENT);

        Toast.makeText(this, "New game started!", Toast.LENGTH_SHORT).show();
    }
}