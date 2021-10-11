package com.example.headsup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var addCelebrity: Button
    lateinit var submit: Button
    lateinit var celebrityName: EditText
    lateinit var rvMain: RecyclerView
    val celebrityList = arrayListOf<CelebrityDataItem>()
    lateinit var adapter: RecyclerViewAdapter

    val retrofitBuilder: ApiInterface = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dojo-recipes.herokuapp.com/")
            .build().create(ApiInterface::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addCelebrity = findViewById(R.id.btn_add)
        submit = findViewById(R.id.btn_submit)
        celebrityName = findViewById(R.id.ed_celebrity_name)
        rvMain = findViewById(R.id.rvMain)
        rvMain.layoutManager = LinearLayoutManager(this)

        CoroutineScope(IO).launch {
            fetchData()
        }


        addCelebrity.setOnClickListener {
            startActivity(Intent(this, AddCelebrity::class.java))
        }

        submit.setOnClickListener {
            if(celebrityName.text.isNotEmpty()){
                var found: CelebrityDataItem? = null

                for (c in celebrityList){
                    if(c.name == celebrityName.text.toString()){
                        found = c
                        break
                    }
                }

                if(found != null){
                    val intent = Intent(this, UpdateDeleteCelebrity::class.java)
                    intent.putExtra("id", found.pk)
                    intent.putExtra("name", found.name)
                    intent.putExtra("taboo1", found.taboo1)
                    intent.putExtra("taboo2", found.taboo2)
                    intent.putExtra("taboo3", found.taboo3)

                    startActivity(intent)
                }
            }
            else
                Toast.makeText(this, "You have to enter a celebrity name", Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchData() {

        try {
            val retrofitResult = retrofitBuilder.getData()

            retrofitResult.enqueue(object : Callback<List<CelebrityDataItem>?> {
                override fun onResponse(call: Call<List<CelebrityDataItem>?>, response: Response<List<CelebrityDataItem>?>) {
                    for (c in response.body()!!)
                        celebrityList.add(CelebrityDataItem(c.name, c.taboo1, c.taboo2, c.taboo3, c.pk))
                    adapter = RecyclerViewAdapter(celebrityList)
                    rvMain.adapter = adapter
                }

                override fun onFailure(call: Call<List<CelebrityDataItem>?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error occur", Toast.LENGTH_LONG).show()
                }
            })

        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, "Error occur", Toast.LENGTH_LONG).show()

        }

    }
}