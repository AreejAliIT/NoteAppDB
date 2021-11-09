package com.example.noteappdb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappdb.*

class RVA(val activity: FragmentHome) :
    RecyclerView.Adapter<RVA.ViewHolder>(){

    private var notes = emptyList<Notes>()

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
                with(activity.sharedPreferences.edit()) {
                    putString("NoteId", note.id.toString())
                    apply()
                }
               activity.findNavController().navigate(R.id.action_fragmentHome_to_fragmentNew)
            }
            btnDel.setOnClickListener { activity.myViewModel.deleteNote(note.id)} // DELETE data by mainActivity and view model
        }
    }
    override fun getItemCount() = notes.size

    fun update(note: List<Notes>){
        this.notes = note
        notifyDataSetChanged()
    }
}


