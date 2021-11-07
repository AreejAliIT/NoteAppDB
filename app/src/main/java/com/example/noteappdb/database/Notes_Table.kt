package com.example.noteappdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note_Table")
 data class Notes (
      @PrimaryKey (autoGenerate = true) val id :Int,
      val noteText: String
    )