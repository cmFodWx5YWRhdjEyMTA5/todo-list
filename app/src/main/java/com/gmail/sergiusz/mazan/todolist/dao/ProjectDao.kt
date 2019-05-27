package com.gmail.sergiusz.mazan.todolist.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ProjectDao {

    @Insert
    fun insert(project : Project)

    @Query("select * from project")
    fun getAllProjects() : LiveData<List<Project>>
}