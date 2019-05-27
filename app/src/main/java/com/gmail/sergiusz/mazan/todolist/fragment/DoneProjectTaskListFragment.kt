package com.gmail.sergiusz.mazan.todolist.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle

class DoneProjectTaskListFragment : DoneTaskListFragment() {

    override fun isInProject(): Boolean {
        return true
    }

    companion object {
        fun newInstance(projectId : Long) : DoneProjectTaskListFragment {

            val fragment = DoneProjectTaskListFragment()
            val bundle = Bundle()
            bundle.putLong("projectId", projectId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setObservers(savedInstanceState: Bundle?) {
        model.setProjectId(arguments!!.getLong("projectId"))
        model.doneProjectTasks.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }

    override fun isDateVisible(): Boolean = true

}