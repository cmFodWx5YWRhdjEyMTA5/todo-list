package com.gmail.sergiusz.mazan.todolist.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle

class OverdueTaskListFragment : UndoneTaskListFragment() {

    override fun isDateVisible(): Boolean {
        return true
    }

    override fun setObservers(savedInstanceState: Bundle?) {
        model.overdueTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }
}