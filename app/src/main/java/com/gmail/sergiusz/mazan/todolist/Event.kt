package com.gmail.sergiusz.mazan.todolist

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "event")
data class Event(
    @ColumnInfo(name = "event_description") @NonNull var description : String,
    @ColumnInfo(name = "event_date") @NonNull var date : LocalDate,
    @ColumnInfo(name = "event_time") var time : LocalTime?
) : Serializable {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "event_id") var id : Int = 0
}