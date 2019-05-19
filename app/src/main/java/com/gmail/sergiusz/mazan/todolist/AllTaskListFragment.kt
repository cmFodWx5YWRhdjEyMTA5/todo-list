package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.Observer

class AllTaskListFragment : UndoneTaskListFragment() {

    override fun setObservers() {
        model.allUndoneTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }

    override fun isDateVisible(): Boolean {
        return true
    }
}