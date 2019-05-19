package com.gmail.sergiusz.mazan.todolist

import android.arch.persistence.room.*
import android.content.Context

@Database(entities = [Task::class], version = 3)
@TypeConverters(*[LocalDateConverter::class, LocalTimeConverter::class])
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao() : TaskDao

    companion object {

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context : Context) : TaskDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, TaskDatabase::class.java,
                    "task_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}