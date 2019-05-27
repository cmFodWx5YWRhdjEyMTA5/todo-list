package com.gmail.sergiusz.mazan.todolist.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle

class UndoneProjectFragment : UndoneTaskListFragment() {

    companion object {
        fun newInstance(projectId : Long) : UndoneProjectFragment {

            val fragment = UndoneProjectFragment()
            val bundle = Bundle()
            bundle.putLong("projectId", projectId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setObservers(savedInstanceState: Bundle?) {
        model.setProjectId(arguments!!.getLong("projectId"))
        model.undoneProjectTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }

    override fun isDateVisible(): Boolean = true

}