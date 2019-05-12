package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.Observer

class AllTaskListFragment : TaskListFragment() {

    override fun setObservers() {
        model.allTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }

    override fun isDateVisible(): Boolean {
        return true
    }
}