package com.example.headsup

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter(private val celebrityList: List<CelebrityDataItem>): RecyclerView.Adapter<RecyclerViewAdapter.ViewItemHolder>() {
    class ViewItemHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemHolder {
        return ViewItemHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_row,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewItemHolder, position: Int) {
        val celebrity = celebrityList[position]

        holder.itemView.apply {
            celebrity_name.text = celebrity.name
            taboo1.text = celebrity.taboo1
            taboo2.text = celebrity.taboo2
            taboo3.text = celebrity.taboo3

            recyclerView.setOnClickListener {
                val intent = Intent(holder.itemView.context, UpdateDeleteCelebrity::class.java)
                intent.putExtra("id", celebrity.pk)
                intent.putExtra("name", celebrity.name)
                intent.putExtra("taboo1", celebrity.taboo1)
                intent.putExtra("taboo2", celebrity.taboo2)
                intent.putExtra("taboo3",  celebrity.taboo3)

                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = celebrityList.size
}