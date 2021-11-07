package com.example.noteappdb.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteappdb.Notes

@Dao
interface Note_Dao {

    @Query("SELECT * FROM Note_Table ORDER BY id ASC")
    fun getAllNote(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Notes)
    @Update
    suspend fun updateNote(note: Notes)
    @Delete
    suspend fun deleteNote(note: Notes)
}