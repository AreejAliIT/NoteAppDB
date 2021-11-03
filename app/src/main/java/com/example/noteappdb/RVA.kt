package com.example.noteappdb

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVA(val activity: MainActivity,val notes:ArrayList<NoteModel>) :
    RecyclerView.Adapter<RVA.ViewHolder>(){

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)
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
            var btnEdit = findViewById<TextView>(R.id.btnEdit)
            var btnDel = findViewById<TextView>(R.id.btnDel)
            //set values
            tv.text = note.noteText
            btnEdit.setOnClickListener {
                activity.raiseDialog(note.id)
            }
            btnDel.setOnClickListener {
                activity.deleteNote(note.id)
            }
        }
    }
    override fun getItemCount() = notes.size
}
