package com.example.noteappdb

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBH(context: Context): SQLiteOpenHelper(context, "MyDB2", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
    // 1 create table
        db?.execSQL("CREATE TABLE Note_Table2 ( ID INTEGER PRIMARY KEY, Note TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
    }
    //3
    fun save(note : NoteModel): Long {

        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put("Note" , note.noteText)
        val status = db.insert("Note_Table2", null, cv)
        return status
    }
    fun retrieve(): ArrayList<NoteModel>{
        val noteList: ArrayList<NoteModel> = ArrayList()
        val selectQuery = "SELECT * FROM Note_Table2"

        val db = this.readableDatabase

        var cursor: Cursor

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var noteText: String

        if(cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex("ID"))
                noteText = cursor.getString(cursor.getColumnIndex("Note"))

                val note = NoteModel(id = id, noteText = noteText)
                noteList.add(note)
            } while (cursor.moveToNext())
        }
        return noteList
    }
    fun update(note: NoteModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("Note", note.noteText)
        val success = db.update("Note_Table2", contentValues, "ID = ${note.id}", null)
        db.close()
        return success
    }
    fun deleteNote(note: NoteModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("ID", note.id)
        val success = db.delete("Note_Table2", "ID = ${note.id}", null)
        db.close()
        return success
    }
}