package com.example.noteappdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappdb.adapters.RVA
import com.example.noteappdb.database.NoteRepo
import com.example.noteappdb.database.Note_DB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//Data class  Model
data class NoteModel(val id: Int, val noteText: String)

class MainActivity : AppCompatActivity() {

    //private val myViewModel by lazy { ViewModelProvider(this).get( ViewModel::class.java) }
    private val dao by lazy { Note_DB.getDatabase(this).noteDao() }
    private val repo by lazy { NoteRepo(dao) }

    private lateinit var etNote : EditText
    private lateinit var rv: RecyclerView
    private lateinit var noteList: List<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         etNote = findViewById(R.id.etNote)
         var btn = findViewById<Button>(R.id.btn)
        //init list
        noteList = listOf()
        // save btn clicked
        btn.setOnClickListener{
            var noteToSave = etNote.text.toString()
            postNote(noteToSave)
            etNote.text.clear()
            etNote.clearFocus()
            updateRV()
        }
        // call get fun
        getItemsList()
        //set RV and update
        rv = findViewById(R.id.rv)
        updateRV()

//        myViewModel.get.observe(this, {
//            varbeleiwant -> etNote.text = varbeleiwant.toString
//        })
    }

    private fun updateRV(){
        rv.adapter = RVA(this, noteList)
        rv.layoutManager = LinearLayoutManager(this)
    }


    private fun postNote(noteToSave :String) {
        // add note and save it in db
//        dbhr.save(NoteModel(0,noteToSave))
        CoroutineScope(IO).launch { repo.addNote(Notes(0,noteToSave))}
    }

    private fun getItemsList(){
////        dbhr.retrieve()
//        var list = listOf<Notes>()
//        CoroutineScope(IO).launch {
//            list = dao.getAllNote()
//        }
//        updateRV()
        CoroutineScope(IO).launch {
            val data = async {
                repo.getNotes
            }.await()
            if(data.isNotEmpty()){
                noteList = data
                updateRV()
            }else{
                Log.e("MainActivity", "Unable to get data", )
            }
        }
    }
    private fun updateNote(id : Int, newNote: String) {
        // update note in db
        CoroutineScope(IO).launch { repo.updateNote(Notes(id , newNote))}
        etNote.text.clear()
        updateRV()
    }
    fun deleteNote(noteID: Int){
//        dbhr.deleteNote(NoteModel(noteID, ""))
        CoroutineScope(IO).launch { repo.deleteNote(Notes(noteID, ""))}
        updateRV()
    }

    fun raiseDialog(id: Int){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", android.content.DialogInterface.OnClickListener {
                    _, _ -> run { updateNote(id, updatedNote.text.toString())
                                  updateRV()
                                }
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
