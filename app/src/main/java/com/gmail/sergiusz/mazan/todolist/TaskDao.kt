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

    @Query("SELECT * from task where task_date = :date")
    fun gettasksOfADay(date : LocalDate) : LiveData<List<Task>>
}