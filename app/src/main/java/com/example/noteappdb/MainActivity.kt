package com.example.noteappdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
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

     val myViewModel by lazy { ViewModelProvider(this).get( ViewModel::class.java) }

    private lateinit var etNote: EditText
    private lateinit var rv: RecyclerView
    private lateinit var rvAdapter :RVA
    private lateinit var noteList: List<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNote = findViewById(R.id.etNote)
        var btn = findViewById<Button>(R.id.btn)

        // use live data and update rv in RVA
        myViewModel.getItemsList().observe(this, { // get data from view model
                notes -> rvAdapter.update(notes)
        })

        // save btn clicked
        btn.setOnClickListener {
            var noteToSave = etNote.text.toString()
            myViewModel.postNote(noteToSave) // view model to post note
            etNote.text.clear()
            etNote.clearFocus()
        }
        // set and update RV
        rv = findViewById(R.id.rv)
        rvAdapter = RVA(this)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this)
    }
    fun raiseDialog(id: Int){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", android.content.DialogInterface.OnClickListener {
                    _, _ -> myViewModel.updateNote(id, updatedNote.text.toString()) // UPDATE data by view model

            })
            .setNegativeButton("Cancel", android.content.DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }


//    private fun postNote(noteToSave :String) {
//        // add note and save it in db
////        dbhr.save(NoteModel(0,noteToSave))
//        CoroutineScope(IO).launch { repo.addNote(Notes(0,noteToSave))}
//    }

//    private fun getItemsList(){
//////        dbhr.retrieve()
////        var list = listOf<Notes>()
////        CoroutineScope(IO).launch {
////            list = dao.getAllNote()
////        }
////        updateRV()
//        CoroutineScope(IO).launch {
//            val data = async {
//                repo.getNotes
//            }.await()
//            if(data.isNotEmpty()){
//                noteList = data
//                updateRV()
//            }else{
//                Log.e("MainActivity", "Unable to get data", )
//            }
//        }
//    }
//    private fun updateNote(id : Int, newNote: String) {
//        // update note in db
//        CoroutineScope(IO).launch { repo.updateNote(Notes(id , newNote))}
//        etNote.text.clear()
//        updateRV()
//    }
//    fun deleteNote(noteID: Int){
////        dbhr.deleteNote(NoteModel(noteID, ""))
//        CoroutineScope(IO).launch { repo.deleteNote(Notes(noteID, ""))}
//        updateRV()
//    }


}
