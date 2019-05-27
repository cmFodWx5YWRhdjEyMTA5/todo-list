package com.gmail.sergiusz.mazan.todolist.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ProjectDao {

    @Delete
    fun delete(project : Project)

    @Insert
    fun insert(project : Project)

    @Update
    fun update(project : Project)

    @Query("select * from project")
    fun getAllProjects() : LiveData<List<Project>>
}