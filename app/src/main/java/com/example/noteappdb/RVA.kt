package com.example.noteappdb

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVA(context: Context ,val notes:ArrayList<NoteModel>) :
    RecyclerView.Adapter<RVA.ViewHolder>(){

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)
    // for binding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var note = notes[position]
        holder.itemView.apply {
            // for binding
            var tv = findViewById<TextView>(R.id.tvNote)
            tv.text = note.noteText
        }
    }
    override fun getItemCount() = notes.size
}
