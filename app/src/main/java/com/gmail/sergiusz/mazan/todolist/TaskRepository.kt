package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import java.time.LocalDate

class TaskRepository(private val taskDao :TaskDao) {

    val allUndoneTasks = taskDao.getAllUndoneTasks()

    val allDoneTasks = taskDao.getAllDoneTask()

    fun getUndoneTasksFromADay(date : LocalDate) : LiveData<List<Task>> {
        return taskDao.getUndoneTasksFromDay(date)
    }

    fun getUndoneTasksEarlierThan(date : LocalDate) : LiveData<List<Task>> {
        return taskDao.getUndoneTasksEarlierThan(date)
    }

    @WorkerThread
    fun insert(task : Task) = taskDao.insert(task)

    @WorkerThread
    fun delete(task: Task) = taskDao.delete(task)

    @WorkerThread
    fun update(task: Task) =  taskDao.update(task)
}