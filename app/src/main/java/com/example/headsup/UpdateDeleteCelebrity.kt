package com.example.headsup

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDeleteCelebrity : AppCompatActivity() {

    lateinit var celebrityName: EditText
    lateinit var taboo1: EditText
    lateinit var taboo2: EditText
    lateinit var taboo3: EditText
    lateinit var updateButton: Button
    lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete_celebrity)

        celebrityName = findViewById(R.id.ed_name)
        taboo1 = findViewById(R.id.ed_taboo1)
        taboo2 = findViewById(R.id.ed_taboo2)
        taboo3 = findViewById(R.id.ed_taboo3)
        updateButton = findViewById(R.id.btn_update_celebrity)
        deleteButton = findViewById(R.id.btn_delete_celebrity)


        val id = intent.getIntExtra("id", -1)
        var name = intent.getStringExtra("name")
        var tabo1 = intent.getStringExtra("taboo1")
        var tabo2 = intent.getStringExtra("taboo2")
        var tabo3 = intent.getStringExtra("taboo3")

        celebrityName.hint = name
        taboo1.hint = tabo1
        taboo2.hint = tabo2
        taboo3.hint = tabo3



        deleteButton.setOnClickListener {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Are you sure you want to delete $name?")
            alert.setPositiveButton("Yes"){_,_ ->
                val retrofitResult = MainActivity().retrofitBuilder.deleteData(id)
                retrofitResult.enqueue(object : Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        Toast.makeText(this@UpdateDeleteCelebrity, "deleted successfully", Toast.LENGTH_LONG).show()

                        startActivity(Intent(this@UpdateDeleteCelebrity, MainActivity::class.java))
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Toast.makeText(this@UpdateDeleteCelebrity, "Can't update", Toast.LENGTH_LONG).show()

                    }
                })
            }
            alert.setNegativeButton("No"){_,_ -> }

            alert.show()
        }

        updateButton.setOnClickListener {
            if(celebrityName.text.isNotEmpty() || taboo1.text.isNotEmpty() || taboo2.text.isNotEmpty() || taboo3.text.isNotEmpty()){
                name = check(celebrityName.text.toString(), name.toString())
                tabo1 = check(taboo1.text.toString(), tabo1.toString())
                tabo2 = check(taboo2.text.toString(), tabo2.toString())
                tabo3 = check(taboo3.text.toString(), tabo3.toString())
                val retrofitResult = MainActivity().retrofitBuilder.updateData(id, CelebrityDataItem(name.toString(), tabo1.toString(), tabo2.toString(), tabo3.toString(), id))

                retrofitResult.enqueue(object : Callback<CelebrityData?> {
                    override fun onResponse(call: Call<CelebrityData?>, response: Response<CelebrityData?>) {
                        Toast.makeText(this@UpdateDeleteCelebrity, "Updated successfully", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<CelebrityData?>, t: Throwable) {
                        Toast.makeText(this@UpdateDeleteCelebrity, "Error occur", Toast.LENGTH_LONG).show()
                    }
                })
            }
            else
                Toast.makeText(this@UpdateDeleteCelebrity, "Same data", Toast.LENGTH_LONG).show()

        }
    }

    fun check(str: String, str2: String): String{
        if(str.isNotEmpty())
            return str
        return str2
    }
}