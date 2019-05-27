package com.gmail.sergiusz.mazan.todolist.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle

class AllDoneTaskListFragment : DoneTaskListFragment() {

    override fun setObservers(savedInstanceState: Bundle?) {
        model.allDoneTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }

    override fun isDateVisible(): Boolean {
        return true
    }
}