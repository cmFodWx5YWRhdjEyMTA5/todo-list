package com.gmail.sergiusz.mazan.todolist.dao

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.io.Serializable
import java.time.LocalDate

@Entity(tableName = "project")
data class Project(
    @ColumnInfo(name = "name") @NonNull var name : String,
    @ColumnInfo(name = "deadline") var deadline : LocalDate?
) : Serializable {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "project_id") var id : Long = 0
}