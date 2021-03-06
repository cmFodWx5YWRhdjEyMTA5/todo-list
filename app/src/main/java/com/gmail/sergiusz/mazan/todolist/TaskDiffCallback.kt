package com.gmail.sergiusz.mazan.todolist

import android.support.v7.util.DiffUtil
import com.gmail.sergiusz.mazan.todolist.dao.Task

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(first: Task, second: Task): Boolean {
        return first.id == second.id
    }

    override fun areContentsTheSame(first: Task, second: Task): Boolean {
        return first == second
    }
}