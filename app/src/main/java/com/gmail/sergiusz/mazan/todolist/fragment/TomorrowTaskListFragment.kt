package com.gmail.sergiusz.mazan.todolist.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle

class TomorrowTaskListFragment : UndoneTaskListFragment() {

    override fun isDateVisible(): Boolean {
        return false
    }

    override fun setObservers(savedInstanceState: Bundle?) {
        model.tomorrowTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }
}