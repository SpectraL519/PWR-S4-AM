package com.circular_progress_bar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBarView: CircularProgressBarView = findViewById(R.id.circular_progress_bar)
        val progressSeekBar: SeekBar = findViewById(R.id.progress_sb)

        progressSeekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    progressBarView.animateProgress((progress - seekBar.min) / (seekBar.max - seekBar.min).toFloat())
                }

                override fun onStartTrackingTouch(seek: SeekBar) {}
                override fun onStopTrackingTouch(seek: SeekBar) {}
            }
        )
    }
}