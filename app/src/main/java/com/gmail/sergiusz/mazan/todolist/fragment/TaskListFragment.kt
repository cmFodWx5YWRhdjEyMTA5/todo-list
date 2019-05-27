package com.gmail.sergiusz.mazan.todolist.fragment

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.gmail.sergiusz.mazan.todolist.*
import com.gmail.sergiusz.mazan.todolist.activity.AddEditProjectTaskActivity
import com.gmail.sergiusz.mazan.todolist.activity.AddEditTaskActivity
import com.gmail.sergiusz.mazan.todolist.activity.MainActivity
import com.gmail.sergiusz.mazan.todolist.adapter.TaskListAdapter
import com.gmail.sergiusz.mazan.todolist.dao.Task

abstract class TaskListFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    protected lateinit var model : TaskViewModel
    protected lateinit var adapter : TaskListAdapter
    private var actionMode : ActionMode? = null
    private var tasks : ArrayList<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
                ViewModelProviders.of(this).get(TaskViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        adapter = TaskListAdapter(object :
            TaskListAdapter.TaskItemClickListener {
            override fun onItemClick(task: Task, view: View): Boolean {
                if(isInProject()) {
                    val intent = Intent(activity, AddEditProjectTaskActivity::class.java)
                    intent.putExtra("taskToEdit", task)
                    startActivityForResult(intent, MainActivity.EDIT_TASK_REQUEST)
                } else {
                    val intent = Intent(activity, AddEditTaskActivity::class.java)
                    intent.putExtra("taskToEdit", task)
                    startActivityForResult(intent, MainActivity.EDIT_TASK_REQUEST)
                }
                return true
            }

        }, isDateVisible())

        setObservers(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        recyclerView = rootView.findViewById(R.id.task_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        val tracker = SelectionTracker.Builder<Long>(
            "mySelection",
            recyclerView,
            TaskKeyProvider(),
            TaskDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        val actionModeCallback = object : ActionMode.Callback {

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                val menuInflater: MenuInflater = mode.menuInflater
                menuInflater.inflate(R.menu.action_menu, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.delete_action -> {
                        for(task in tasks) {
                            model.delete(task)
                        }
                        mode.finish()
                        return true
                    }
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                actionMode = null
                tasks.clear()
                tracker.clearSelection()
            }
        }

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onItemStateChanged(key: Long, selected: Boolean) {
                    if(actionMode == null) {
                        actionMode = (activity as AppCompatActivity).startSupportActionMode(actionModeCallback)
                    }

                    if(tracker.selection.isEmpty) {
                        if(actionMode != null) {
                            actionMode!!.finish()
                        }
                    }

                    if(selected)
                        tasks.add(adapter.getItemAtPosition(key.toInt()))
                    else
                        tasks.remove(adapter.getItemAtPosition(key.toInt()))
                }
            })

        adapter.tracker = tracker

        ItemTouchHelper(getCallback()).attachToRecyclerView(recyclerView)

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == MainActivity.EDIT_TASK_REQUEST) {
            val task : Task = data?.getSerializableExtra("task") as Task
            model.update(task)
        }
    }

    protected abstract fun setObservers(savedInstanceState: Bundle?)
    protected abstract fun isDateVisible() : Boolean
    protected abstract fun getCallback() : ItemTouchHelper.Callback
    protected abstract fun isInProject() : Boolean
}