package com.gmail.sergiusz.mazan.todolist

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "task")
data class Task(
    @ColumnInfo(name = "task_description") @NonNull var description : String,
    @ColumnInfo(name = "task_date") @NonNull var date : LocalDate,
    @ColumnInfo(name = "task_time") var time : LocalTime?
) : Serializable {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "task_id") var id : Int = 0
}