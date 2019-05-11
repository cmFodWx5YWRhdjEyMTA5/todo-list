package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import java.time.LocalDate

@Dao
interface TaskDao {

    @Insert
    fun insert(task : Task)

    @Delete
    fun delete(task: Task)

    @Query("select * from task order by task_time")
    fun getAllTasks() : LiveData<List<Task>>

    @Query("select * from task where task_date = :date order by task_time")
    fun getTasksFromDay(date : LocalDate) : LiveData<List<Task>>

    @Query("select * from task where task_date < :date order by task_time")
    fun getTasksEarlierThan(date : LocalDate) : LiveData<List<Task>>
}