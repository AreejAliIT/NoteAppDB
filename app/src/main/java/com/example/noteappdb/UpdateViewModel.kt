package com.example.noteappdb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.noteappdb.database.NoteRepo
import com.example.noteappdb.database.Note_DB
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateViewModel(application: Application): AndroidViewModel(application) {

    private var repo : NoteRepo
    private var noteList : LiveData<List<Notes>>

    init {
        val dao = Note_DB.getDatabase(application).noteDao()
        repo = NoteRepo(dao)
        noteList = repo.getNotes
    }

    fun updateNote(id : Int, newNote: String) {
        CoroutineScope(Dispatchers.IO).launch { repo.updateNote(Notes(id , newNote))}
    }
}