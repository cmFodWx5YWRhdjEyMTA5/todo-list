package com.gmail.sergiusz.mazan.todolist.dao

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import java.time.LocalDate

class TaskRepository(private val taskDatabase: TaskDatabase) {

    val allUndoneTasks = taskDatabase.taskDao().getAllUndoneTasks()

    val allDoneTasks = taskDatabase.taskDao().getAllDoneTask()

    val allProjects = taskDatabase.projectDao().getAllProjects()

    fun getUndoneTasksFromADay(date : LocalDate) : LiveData<List<Task>> {
        return taskDatabase.taskDao().getUndoneTasksFromDay(date)
    }

    fun getUndoneTasksEarlierThan(date : LocalDate) : LiveData<List<Task>> {
        return taskDatabase.taskDao().getUndoneTasksEarlierThan(date)
    }

    fun getUndoneTasksFromADaySync(date : LocalDate) : List<Task> {
        return taskDatabase.taskDao().getUndoneTasksFromDaySync(date)
    }

    fun getUndoneTasksEarlierThanSync(date : LocalDate) : List<Task> {
        return taskDatabase.taskDao().getUndoneTasksEarlierThanSync(date)
    }

    fun getTaskWithId(id : Long) : Task = taskDatabase.taskDao().getTask(id)

    fun getDoneTasksOfProject(projectId : Long) = taskDatabase.taskDao().getDoneTasksOfProject(projectId)

    fun getUndoneTasksOfProject(projectId : Long) = taskDatabase.taskDao().getUndoneTasksOfProject(projectId)

    @WorkerThread
    fun insert(task : Task) : Long = taskDatabase.taskDao().insert(task)

    @WorkerThread
    fun delete(task: Task) = taskDatabase.taskDao().delete(task)

    @WorkerThread
    fun update(task: Task) =  taskDatabase.taskDao().update(task)


    @WorkerThread
    fun insert(project : Project) = taskDatabase.projectDao().insert(project)
}