package com.example.headsup

import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
class GameActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var list: MutableList<List<String>>

    private lateinit var txt: TextView
    private lateinit var tvScore: TextView
    private lateinit var cName: TextView
    private lateinit var t1: TextView
    private lateinit var t2: TextView
    private lateinit var t3: TextView
    private lateinit var linear: LinearLayout
    private lateinit var linear1: LinearLayout
    private lateinit var linear0: LinearLayout
    lateinit var tvSeconds: TextView

    private var size = 0
    private var randoms = arrayListOf<Int>()
    private var idx = 0
    private var score = 0


    private var timerFinish = false
    private var timerLeft = 4000L

    var canPlay = true
    private var timeLeft = 60000L
    private lateinit var countDownTimer: CountDownTimer





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        startTimer()

        this.title = "Time: $timeLeft"

        sharedPreferences = getSharedPreferences("Alsultan_Heads_up_game", MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        txt = findViewById(R.id.txt)
        tvScore = findViewById(R.id.score)
        cName = findViewById(R.id.name)
        t1 = findViewById(R.id.t1)
        t2 = findViewById(R.id.t2)
        t3 = findViewById(R.id.t3)
        linear = findViewById(R.id.linearLayout)
        linear1 = findViewById(R.id.linear1)
        linear0 = findViewById(R.id.linear0)
        tvSeconds = findViewById(R.id.seconds)

        list = mutableListOf()
        size = intent.getIntExtra("size", -1)

        for (i in 0 until size) {
            intent.getStringExtra("c$i")?.split("|||")?.let { list.add(it) }
            val edit = sharedPreferences.edit()
            edit.putString("c$i", "${list[i][0]}|||${list[i][1]}|||${list[i][2]}|||${list[i][3]}")
        }
        edit.apply()
        randoms = (0..size).shuffled().take(size) as ArrayList<Int>
        for(i in 0 until size)
            edit.putInt("r$i", randoms[i])
        edit.apply()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (size > idx && canPlay && timerFinish) {
            if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
                val rnd = sharedPreferences.getInt("r$idx", -1)
                val selectedItem = sharedPreferences.getString("c$rnd", "")!!.split("|||")
                cName.text = selectedItem[0]
                t1.text = selectedItem[1]
                t2.text = selectedItem[2]
                t3.text = selectedItem[3]
                linear.visibility = View.VISIBLE
                linear1.visibility = View.GONE

            }
            else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
                idx++
                score++
                linear.visibility = View.GONE
                linear1.visibility = View.VISIBLE
                tvScore.text = "Your score: $size/$score"
            }
        }
        else {
            txt.text = "Game finish"
            linear1.visibility = View.VISIBLE
            tvScore.text = "Your score: $size/$score"
        }
    }



    private fun startTime(){
        countDownTimer = object : CountDownTimer(timeLeft, 1000){
            override fun onTick(p0: Long) {
                timeLeft = p0
                updateCountDownTimer()
            }

            override fun onFinish() {
                linear.visibility = View.GONE
                linear1.visibility = View.VISIBLE
                canPlay = false
                tvScore.text = "Your score: $size/$score"
            }
        }.start()
    }

    private fun updateCountDownTimer(){
        this@GameActivity.title = "Time: ${timeLeft/1000}"
    }


    private fun startTimer(){
        countDownTimer = object : CountDownTimer(timerLeft, 1000){
            override fun onTick(p0: Long) {
                timerLeft = p0
                updateSeconds()
            }

            override fun onFinish() {
                linear0.visibility = View.GONE
                linear1.visibility = View.VISIBLE
                timerFinish = true
                startTime()
                tvScore.text = "Your score: $size/$score"
            }
        }.start()
    }

    private fun updateSeconds(){
        tvSeconds.text = "${timerLeft/1000} Seconds"
    }




}