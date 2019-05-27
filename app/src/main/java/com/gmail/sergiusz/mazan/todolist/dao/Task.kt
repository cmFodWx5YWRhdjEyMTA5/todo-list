package com.gmail.sergiusz.mazan.todolist.dao

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
    @ColumnInfo(name = "task_date") var date : LocalDate?,
    @ColumnInfo(name = "task_time") var time : LocalTime?,
    @ColumnInfo(name = "task_priority") var priority: Int,
    @ColumnInfo(name = "is_done") var isDone : Boolean,
    @ColumnInfo(name = "project_id") var projectId : Long? = null
) : Serializable {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "task_id") var id : Long = 0
}