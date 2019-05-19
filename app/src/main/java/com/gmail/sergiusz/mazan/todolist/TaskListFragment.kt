package com.gmail.sergiusz.mazan.todolist

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class TaskListFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    protected lateinit var model : TaskViewModel
    protected lateinit var adapter : TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
                ViewModelProviders.of(this).get(TaskViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        adapter = TaskListAdapter(object : TaskListAdapter.TaskItemClickListener {
            override fun onItemClick(task: Task, view: View) : Boolean {
                val intent = Intent(activity, AddEditTaskActivity::class.java)
                intent.putExtra("taskToEdit", task)
                startActivityForResult(intent, MainActivity.EDIT_TASK_REQUEST)
                return true
            }

        }, isDateVisible())

        setObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        recyclerView = rootView.findViewById(R.id.task_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        ItemTouchHelper(getCallback()).attachToRecyclerView(recyclerView)

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == MainActivity.EDIT_TASK_REQUEST) {
            val task : Task = data?.getSerializableExtra("task") as Task
            model.update(task)
        }
    }

    protected abstract fun setObservers()
    protected abstract fun isDateVisible() : Boolean
    protected abstract fun getCallback() : ItemTouchHelper.Callback
}