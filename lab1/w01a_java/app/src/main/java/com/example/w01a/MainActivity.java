package com.example.w01a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int n;
    private int m;
    private int score;
    private Random generator;

    private TextView points;
    private Button leftButton;
    private Button rightButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();
        this.roll();
    }

    private void init () {
        this.score = 0;
        this.generator = new Random();

        this.points = findViewById(R.id.points);
        this.leftButton = findViewById(R.id.leftButton);
        this.rightButton = findViewById(R.id.rightButton);
    }

    private void roll() {
        this.points.setText("Punkty: " + this.score);

        this.n = this.generator.nextInt(10);
        this.m = this.generator.nextInt(10);
        this.leftButton.setText("" + this.n);
        this.rightButton.setText("" + this.m);
    }

    public void leftClick(View view) {
        if (this.n >= this.m) {
            this.score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }
        else {
            this.score--;
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        this.roll();
    }

    public void rightClick(View view) {
        if (this.m >= this.n) {
            this.score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }
        else {
            this.score--;
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        this.roll();
    }
}