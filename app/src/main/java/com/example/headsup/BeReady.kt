package com.example.headsup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView

class BeReady : AppCompatActivity() {

    private var timeLeft = 3000L
    private lateinit var countDownTimer: CountDownTimer
    lateinit var tvSeconds: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_be_ready)

        tvSeconds = findViewById(R.id.seconds)
        setCounterDown()

    }

    private fun setCounterDown(){
        countDownTimer = object: CountDownTimer(timeLeft, 1000){
            override fun onTick(p0: Long) {
                timeLeft -= p0
                updateSeconds()
            }

            override fun onFinish() {

            }

        }
    }

    private fun updateSeconds(){
        tvSeconds.text = "${timeLeft/1000} Seconds"
    }
}