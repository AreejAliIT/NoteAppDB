package com.example.noteappdb

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class RVA(private val videos:Array<Array<String>>) :
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
        var videoName = videos[position][0]
        var videoId =   videos[position][1]
        var thumbnail = videos[position][2]
        Log.d("RV","the thumbnail is --> $thumbnail")
        holder.itemView.apply {
            // for binding

        }
    }
    override fun getItemCount() = videos.size
}
