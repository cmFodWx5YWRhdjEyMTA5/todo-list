package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import java.time.LocalDate

class EventRepository(private val eventDao : EventDao) {

    fun getEventsOfADay(date : LocalDate) : LiveData<List<Event>> = eventDao.getEventsOfADay(date)

    @WorkerThread
    fun insert(event : Event) = eventDao.insert(event)
}