package com.gmail.sergiusz.mazan.todolist.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import java.time.LocalDate

@Dao
interface TaskDao {

    @Insert
    fun insert(task : Task) : Long

    @Delete
    fun delete(task: Task)

    @Query("delete from task where project_id = :projectId")
    fun deleteTasksOfProject(projectId: Long)

    @Update
    fun update(task: Task)

    @Query("select * from task where is_done = 1 and project_id is null order by task_date, task_time")
    fun getAllDoneTask() : LiveData<List<Task>>

    @Query("select * from task where is_done = 0 and project_id is null order by task_date, task_time")
    fun getAllUndoneTasks() : LiveData<List<Task>>

    @Query("select * from task where task_date = :date and is_done = 0 and project_id is null order by task_time")
    fun getUndoneTasksFromDay(date : LocalDate) : LiveData<List<Task>>

    @Query("select * from task where task_date < :date and is_done = 0 and project_id is null order by task_date, task_time")
    fun getUndoneTasksEarlierThan(date : LocalDate) : LiveData<List<Task>>

    @Query("select * from task where task_date = :date and is_done = 0 order by task_time")
    fun getUndoneTasksFromDaySync(date : LocalDate) : List<Task>

    @Query("select * from task where task_date < :date and is_done = 0 order by task_date, task_time")
    fun getUndoneTasksEarlierThanSync(date : LocalDate) : List<Task>

    @Query("select * from task where task_id = :taskId")
    fun getTask(taskId : Long) : Task

    @Query("select * from task where project_id = :projectId and is_done = 1 order by task_date, task_time")
    fun getDoneTasksOfProject(projectId : Long?) : LiveData<List<Task>>

    @Query("select * from task where project_id = :projectId and is_done = 0 order by task_date, task_time")
    fun getUndoneTasksOfProject(projectId : Long?) : LiveData<List<Task>>
}