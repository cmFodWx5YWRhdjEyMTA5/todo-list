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
    val todayTasks : LiveData<List<Task>>
    val tomorrowTasks : LiveData<List<Task>>
    val overdueTasks : LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        todayTasks = repository.getTasksFromADay(LocalDate.now())
        tomorrowTasks = repository.getTasksFromADay(LocalDate.now().plusDays(1))
        overdueTasks = repository.getTasksEarlierThan(LocalDate.now())
    }


    fun insert(task: Task) = scope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    fun delete(task: Task) = scope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}