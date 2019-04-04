package com.gmail.sergiusz.mazan.todolist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import java.time.LocalDate

class EventViewModel(application : Application) : AndroidViewModel(application) {

    private val repository : EventRepository
    val eventsOfADay : LiveData<List<Event>>

    init {
        val eventDao = EventDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDao)
        eventsOfADay = repository.getEventsOfADay(LocalDate.now())
    }
}