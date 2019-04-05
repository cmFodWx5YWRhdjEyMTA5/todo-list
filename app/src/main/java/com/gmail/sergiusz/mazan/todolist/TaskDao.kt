package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import java.time.LocalDate

@Dao
interface TaskDao {

    @Insert
    fun insert(task : Task)

    @Query("select * from task order by task_time")
    fun getAllTasks() : LiveData<List<Task>>
}