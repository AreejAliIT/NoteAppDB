package com.example.noteappdb

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBH(context: Context): SQLiteOpenHelper(context, "MyDB", null, 1) {
    //1
    var sqlLiteDB :SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
    //2

        if (db != null) {
            db.execSQL("CREATE TABLE Note_Table (Note text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
       //3

    }
    fun save(note :String): Long {
        val cv = ContentValues()
        cv.put("Note" , note)
        val status = sqlLiteDB.insert("Note_Table", null, cv)
        return status
    }

}