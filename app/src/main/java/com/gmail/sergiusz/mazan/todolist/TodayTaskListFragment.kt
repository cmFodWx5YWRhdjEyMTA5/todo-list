package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.Observer

class TodayTaskListFragment : TaskListFragment() {

    override fun setObservers() {
        model.todayTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }
}