package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.Observer

class OverdueTaskListFragment : UndoneTaskListFragment() {

    override fun isDateVisible(): Boolean {
        return true
    }

    override fun setObservers() {
        model.overdueTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }
}