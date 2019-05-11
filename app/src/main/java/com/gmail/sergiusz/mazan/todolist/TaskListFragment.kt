package com.gmail.sergiusz.mazan.todolist

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
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

        adapter = TaskListAdapter(object: DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(first: Task, second: Task): Boolean {
                return first.id == second.id
            }

            override fun areContentsTheSame(first: Task, second: Task): Boolean {
                return first == second
            }

        },object : TaskListAdapter.TaskItemClickListener {
            override fun onItemClick(task: Task, view: View) : Boolean {
                //TODO - add edit option
                return false
            }

        })

        setObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        recyclerView = rootView.findViewById(R.id.task_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val taskToRemove = adapter.getItemAtPosition(viewHolder.adapterPosition)
                model.delete(taskToRemove)

                val snackbar = Snackbar.make(rootView, "Task was checked as done", Snackbar.LENGTH_LONG)
                snackbar.setAction("Undo") {
                    model.insert(taskToRemove)
                }

                snackbar.show()
            }

        }).attachToRecyclerView(recyclerView)

        return rootView
    }

    protected abstract fun setObservers()
}