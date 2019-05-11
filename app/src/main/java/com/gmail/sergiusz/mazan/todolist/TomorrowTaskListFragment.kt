package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.Observer

class TomorrowTaskListFragment : TaskListFragment() {

    override fun setObservers() {
        model.tomorrowTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }
}