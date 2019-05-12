package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import java.time.LocalDate

class TaskRepository(private val taskDao :TaskDao) {

    val allTasks = taskDao.getAllTasks()

    fun getTasksFromADay(date : LocalDate) : LiveData<List<Task>> {
        return taskDao.getTasksFromDay(date)
    }

    fun getTasksEarlierThan(date : LocalDate) : LiveData<List<Task>> {
        return taskDao.getTasksEarlierThan(date)
    }

    @WorkerThread
    fun insert(task : Task) = taskDao.insert(task)

    @WorkerThread
    fun delete(task: Task) = taskDao.delete(task)

    @WorkerThread
    fun update(task: Task) =  taskDao.update(task)
}