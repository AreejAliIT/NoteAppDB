package com.example.noteappdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dbhr: DBH
    private lateinit var etNote : EditText
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         dbhr = DBH(this)
         etNote = findViewById(R.id.etNote)
         var btn = findViewById<Button>(R.id.btn)

        //set RV
        rv = findViewById(R.id.rv)
        rv.adapter = RVA(this , getItemsList())
        rv.layoutManager = LinearLayoutManager(this)

        btn.setOnClickListener{postNote()}
    }

    private fun postNote() {
        // add note and save it in db
        var noteToSave = etNote.text.toString()
        dbhr.save(NoteModel(0,noteToSave))
        etNote.text.clear()
        rv.adapter = RVA(this,getItemsList())
        rv.layoutManager = LinearLayoutManager(this)
    }

    private fun getItemsList(): ArrayList<NoteModel> {
        return dbhr.retrieve()
    }
    private fun updateNote(id : Int, newNote: String) {
        // update note in db
        dbhr.update(NoteModel( id, newNote))
        etNote.text.clear()
        rv.adapter = RVA(this,getItemsList())
        rv.layoutManager = LinearLayoutManager(this)
    }
    fun deleteNote(noteID: Int){
        dbhr.deleteNote(NoteModel(noteID, ""))
        rv.adapter = RVA(this,getItemsList())
        rv.layoutManager = LinearLayoutManager(this)
    }
    fun raiseDialog(id: Int){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", android.content.DialogInterface.OnClickListener {
                    _, _ -> updateNote(id, updatedNote.text.toString())
            })
            .setNegativeButton("Cancel", android.content.DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }


}
data class NoteModel(val id: Int, val noteText: String)