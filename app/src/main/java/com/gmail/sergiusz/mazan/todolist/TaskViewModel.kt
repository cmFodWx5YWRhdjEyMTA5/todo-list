package com.gmail.sergiusz.mazan.todolist

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import android.content.Intent
import com.gmail.sergiusz.mazan.todolist.dao.Project
import com.gmail.sergiusz.mazan.todolist.dao.Task
import com.gmail.sergiusz.mazan.todolist.dao.TaskDatabase
import com.gmail.sergiusz.mazan.todolist.dao.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

class TaskViewModel(application : Application) : AndroidViewModel(application) {

    private var job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private val repository : TaskRepository =
        TaskRepository(
            TaskDatabase.getDatabase(application)
        )

    val todayTasks : LiveData<List<Task>>
    val tomorrowTasks : LiveData<List<Task>>
    val overdueTasks : LiveData<List<Task>>
    val allDoneTasks : LiveData<List<Task>>
    val allUndoneTasks : LiveData<List<Task>>
    val allProjects : LiveData<List<Project>>
    val doneProjectTasks : LiveData<List<Task>>
    val undoneProjectTasks : LiveData<List<Task>>
    val projectId : MutableLiveData<Long> = MutableLiveData<Long>()

    init {
        allUndoneTasks = repository.allUndoneTasks
        allDoneTasks = repository.allDoneTasks
        todayTasks = repository.getUndoneTasksFromADay(LocalDate.now())
        tomorrowTasks = repository.getUndoneTasksFromADay(LocalDate.now().plusDays(1))
        overdueTasks = repository.getUndoneTasksEarlierThan(LocalDate.now())
        allProjects = repository.allProjects
        doneProjectTasks = Transformations.switchMap(projectId) {
            repository.getDoneTasksOfProject(it)
        }
        undoneProjectTasks = Transformations.switchMap(projectId) {
            repository.getUndoneTasksOfProject(it)
        }
    }

    fun setProjectId(value : Long) {
        this.projectId.value = value
    }

    fun insert(project : Project) = scope.launch(Dispatchers.IO) {
        repository.insert(project)
    }

    fun insert(task: Task) = scope.launch(Dispatchers.IO) {
        val taskId = repository.insert(task)

        if(task.time != null) {
            val intent = Intent(getApplication(), TaskNotificationReceiver::class.java)
            intent.putExtra("taskId", taskId)

            val pendingIntent = PendingIntent.getBroadcast(getApplication(), taskId.toInt(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmManager = getApplication<Application>()
                .getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.YEAR, task.date.year)
                set(Calendar.MONTH, task.date.monthValue-1)
                set(Calendar.DAY_OF_MONTH, task.date.dayOfMonth)
                set(Calendar.HOUR_OF_DAY, task.time!!.hour)
                set(Calendar.MINUTE, task.time!!.minute)
            }

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }

    fun delete(task: Task) = scope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    fun update(task: Task) = scope.launch(Dispatchers.IO) {
        repository.update(task)

        val intent = Intent(getApplication(), TaskNotificationReceiver::class.java)
        intent.putExtra("taskId", task.id)

        var pendingIntent = PendingIntent.getBroadcast(getApplication(), task.id.toInt(),
            intent, PendingIntent.FLAG_NO_CREATE)

        //Jesli nie ma godziny ustawionej
        if(task.time == null) {
            //Jesli wczesniej faktycznie byl ustawiony alarm
            if (pendingIntent != null) {
                val alarmManager = getApplication<Application>()
                    .getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.cancel(pendingIntent)
            }
        } else {
            pendingIntent = PendingIntent.getBroadcast(getApplication(), task.id.toInt(), intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmManager = getApplication<Application>()
                .getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.YEAR, task.date.year)
                set(Calendar.MONTH, task.date.monthValue-1)
                set(Calendar.DAY_OF_MONTH, task.date.dayOfMonth)
                set(Calendar.HOUR_OF_DAY, task.time!!.hour)
                set(Calendar.MINUTE, task.time!!.minute)
            }

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}