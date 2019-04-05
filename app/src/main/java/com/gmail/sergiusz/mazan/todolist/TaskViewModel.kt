package com.gmail.sergiusz.mazan.todolist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(application : Application) : AndroidViewModel(application) {

    private var job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private val repository : TaskRepository
    val allTasks : LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }


    fun insert(task: Task) = scope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}