package com.example.noteappdb

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteappdb.database.NoteRepo
import com.example.noteappdb.database.Note_DB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ViewModel(application: Application): AndroidViewModel(application) {

    private var repo : NoteRepo
    private var noteList : LiveData<List<Notes>>

    init {
        val dao = Note_DB.getDatabase(application).noteDao()
        repo = NoteRepo(dao)
        noteList = repo.getNotes
    }
    fun postNote(noteToSave :String) {
        CoroutineScope(Dispatchers.IO).launch { repo.addNote(Notes(0,noteToSave))}
    }

    fun getItemsList() : LiveData<List<Notes>>{
     return noteList
    }
    fun updateNote(id : Int, newNote: String) {
        CoroutineScope(Dispatchers.IO).launch { repo.updateNote(Notes(id , newNote))}
    }
    fun deleteNote(noteID: Int){
        CoroutineScope(Dispatchers.IO).launch { repo.deleteNote(Notes(noteID, ""))}
    }

}