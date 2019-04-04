package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import java.time.LocalDate

class TaskRepository(private val taskDao :TaskDao) {

    fun getTasksOfADay(date : LocalDate) : LiveData<List<Task>> = taskDao.gettasksOfADay(date)

    @WorkerThread
    fun insert(task : Task) = taskDao.insert(task)
}