package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.Observer

class TomorrowTaskListFragment : TaskListFragment() {

    override fun isDateVisible(): Boolean {
        return false
    }

    override fun setObservers() {
        model.tomorrowTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }
}