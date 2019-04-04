package com.gmail.sergiusz.mazan.todolist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import java.time.LocalDate

class TaskViewModel(application : Application) : AndroidViewModel(application) {

    private val repository : TaskRepository
    val tasksOfADay : LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        tasksOfADay = repository.getTasksOfADay(LocalDate.now())
    }
}