package com.example.noteappdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var etNote = findViewById<EditText>(R.id.etNote)
        var btn = findViewById<Button>(R.id.btn)
        var noteToSave = ""
        btn.setOnClickListener{
            noteToSave = etNote.text.toString()
            var dbhr = DBH(applicationContext)
            var status = dbhr.save(noteToSave)
            Log.i("AboutDB" , "saved: $status")
        }
    }
}