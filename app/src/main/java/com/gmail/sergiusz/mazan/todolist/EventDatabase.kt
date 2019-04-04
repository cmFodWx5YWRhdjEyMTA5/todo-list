package com.gmail.sergiusz.mazan.todolist

import android.arch.persistence.room.*
import android.content.Context

@Database(entities = [Event::class], version = 1)
@TypeConverters(*[LocalDateConverter::class, LocalTimeConverter::class])
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao() : EventDao

    companion object {

        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getDatabase(context : Context) : EventDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, EventDatabase::class.java,
                    "event_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}