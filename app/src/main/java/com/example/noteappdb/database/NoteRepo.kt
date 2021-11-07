package com.example.noteappdb.database

import androidx.lifecycle.LiveData
import com.example.noteappdb.Notes

class NoteRepo(private val noteDao: Note_Dao) {


        val getNotes: LiveData<List<Notes>> = noteDao.getAllNote()

        suspend fun addNote(note: Notes){
            noteDao.insertNote(note)
        }

        suspend fun updateNote(note: Notes){
            noteDao.updateNote(note)
        }

        suspend fun deleteNote(note: Notes){
            noteDao.deleteNote(note)
        }


}