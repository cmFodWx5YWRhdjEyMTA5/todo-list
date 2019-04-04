package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import java.time.LocalDate

@Dao
interface EventDao {

    @Insert
    fun insert(event : Event)

    @Query("SELECT * from event where event_date = :date")
    fun getEventsOfADay(date : LocalDate) : LiveData<List<Event>>
}