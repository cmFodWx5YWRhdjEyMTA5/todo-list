package com.gmail.sergiusz.mazan.todolist

import android.support.v7.util.DiffUtil
import com.gmail.sergiusz.mazan.todolist.dao.Project

class ProjectDiffCallback : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(first: Project, second: Project): Boolean {
        return first.id == second.id
    }

    override fun areContentsTheSame(first: Project, second: Project): Boolean {
        return first == second
    }
}