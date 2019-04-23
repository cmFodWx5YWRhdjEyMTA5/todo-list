package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.Observer

class OverdueTaskListFragment : TaskListFragment() {

    override fun setObservers() {
        model.overdueTasks.observe(this, Observer { item ->
            item?.let {
                adapter.setTasks(item)
            }
        })    }

}