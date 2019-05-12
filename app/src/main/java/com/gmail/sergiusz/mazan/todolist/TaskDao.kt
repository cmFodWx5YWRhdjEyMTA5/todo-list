package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import java.time.LocalDate

@Dao
interface TaskDao {

    @Insert
    fun insert(task : Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)

    @Query("select * from task order by task_date, task_time")
    fun getAllTasks() : LiveData<List<Task>>

    @Query("select * from task where task_date = :date order by task_time")
    fun getTasksFromDay(date : LocalDate) : LiveData<List<Task>>

    @Query("select * from task where task_date < :date order by task_date, task_time")
    fun getTasksEarlierThan(date : LocalDate) : LiveData<List<Task>>
}