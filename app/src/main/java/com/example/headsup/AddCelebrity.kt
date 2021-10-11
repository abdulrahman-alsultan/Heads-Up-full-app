package com.example.headsup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCelebrity : AppCompatActivity() {

    lateinit var celebrityName: EditText
    lateinit var taboo1: EditText
    lateinit var taboo2: EditText
    lateinit var taboo3: EditText
    lateinit var addButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_celebrity)

        celebrityName = findViewById(R.id.ed_name)
        taboo1 = findViewById(R.id.ed_taboo1)
        taboo2 = findViewById(R.id.ed_taboo2)
        taboo3 = findViewById(R.id.ed_taboo3)
        addButton = findViewById(R.id.btn_add_celebrity)


        addButton.setOnClickListener {
            if(celebrityName.text.isNotEmpty() && taboo1.text.isNotEmpty() && taboo2.text.isNotEmpty() && taboo3.text.isNotEmpty()){
                val retrofitResult = MainActivity().retrofitBuilder.postData(CelebrityDataItem(celebrityName.text.toString(), taboo1.text.toString(), taboo2.text.toString(), taboo3.text.toString(), -1))

                retrofitResult.enqueue(object : Callback<CelebrityData?> {
                    override fun onResponse(call: Call<CelebrityData?>, response: Response<CelebrityData?>) {
                        Toast.makeText(this@AddCelebrity, "Added successfully", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@AddCelebrity, MainActivity::class.java))
                    }

                    override fun onFailure(call: Call<CelebrityData?>, t: Throwable) {
                        Toast.makeText(this@AddCelebrity, "Error occur: $t", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

    }
}