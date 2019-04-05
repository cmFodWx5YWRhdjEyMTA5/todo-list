package com.gmail.sergiusz.mazan.todolist

import android.support.annotation.WorkerThread

class TaskRepository(private val taskDao :TaskDao) {

    val allTasks = taskDao.getAllTasks()

    @WorkerThread
    fun insert(task : Task) = taskDao.insert(task)
}