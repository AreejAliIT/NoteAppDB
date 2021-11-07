package com.example.noteappdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteappdb.Notes

@Database(entities = [Notes::class] , version = 1, exportSchema = false)
 abstract class Note_DB : RoomDatabase() {

     abstract fun noteDao() : Note_Dao

    companion object{
        @Volatile  // writes to this field are immediately visible to other threads
        private var INSTANCE: Note_DB? = null

        fun getDatabase(context: Context): Note_DB{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){  // protection from concurrent execution on multiple threads
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Note_DB::class.java,
                    "Note_Table"
                ).fallbackToDestructiveMigration()  // Destroys old database on version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}