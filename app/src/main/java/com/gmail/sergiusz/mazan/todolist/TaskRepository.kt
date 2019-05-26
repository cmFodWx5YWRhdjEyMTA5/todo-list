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

    fun getUndoneTasksFromADaySync(date : LocalDate) : List<Task> {
        return taskDao.getUndoneTasksFromDaySync(date)
    }

    fun getUndoneTasksEarlierThanSync(date : LocalDate) : List<Task> {
        return taskDao.getUndoneTasksEarlierThanSync(date)
    }

    fun getTaskWithId(id : Long) : Task = taskDao.getTask(id)

    @WorkerThread
    fun insert(task : Task) : Long = taskDao.insert(task)

    @WorkerThread
    fun delete(task: Task) = taskDao.delete(task)

    @WorkerThread
    fun update(task: Task) =  taskDao.update(task)
}