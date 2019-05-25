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

    @Query("select * from task where is_done = 1 order by task_date, task_time")
    fun getAllDoneTask() : LiveData<List<Task>>

    @Query("select * from task where is_done = 0 order by task_date, task_time")
    fun getAllUndoneTasks() : LiveData<List<Task>>

    @Query("select * from task where task_date = :date and is_done = 0 order by task_time")
    fun getUndoneTasksFromDay(date : LocalDate) : LiveData<List<Task>>

    @Query("select * from task where task_date < :date and is_done = 0 order by task_date, task_time")
    fun getUndoneTasksEarlierThan(date : LocalDate) : LiveData<List<Task>>

    @Query("select * from task where task_date = :date and is_done = 0 order by task_time")
    fun getUndoneTasksFromDaySync(date : LocalDate) : List<Task>

    @Query("select * from task where task_date < :date and is_done = 0 order by task_date, task_time")
    fun getUndoneTasksEarlierThanSync(date : LocalDate) : List<Task>
}