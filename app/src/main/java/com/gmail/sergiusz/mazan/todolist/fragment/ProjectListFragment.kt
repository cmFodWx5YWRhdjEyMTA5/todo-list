package com.gmail.sergiusz.mazan.todolist.fragment

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.gmail.sergiusz.mazan.todolist.ProjectDetailsLookup
import com.gmail.sergiusz.mazan.todolist.R
import com.gmail.sergiusz.mazan.todolist.TaskKeyProvider
import com.gmail.sergiusz.mazan.todolist.activity.AddEditProjectActivity
import com.gmail.sergiusz.mazan.todolist.activity.MainActivity
import com.gmail.sergiusz.mazan.todolist.adapter.ProjectListAdapter
import com.gmail.sergiusz.mazan.todolist.dao.Project
import com.gmail.sergiusz.mazan.todolist.dao.Task
import com.gmail.sergiusz.mazan.todolist.dao.TaskViewModel

class ProjectListFragment : Fragment() {


    private lateinit var recyclerView : RecyclerView
    lateinit var model : TaskViewModel
    lateinit var adapter : ProjectListAdapter
    private var actionMode : ActionMode? = null
    private var projects : ArrayList<Project> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this).get(TaskViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        adapter = ProjectListAdapter(object :
            ProjectListAdapter.ProjectItemClickListener {

            override fun onItemClick(project : Project, view: View): Boolean {
                val intent = Intent(activity, AddEditProjectActivity::class.java)
                intent.putExtra("projectToEdit", project)
                startActivityForResult(intent, MainActivity.EDIT_PROJECT_REQUEST)
                return true
            }

        })

        model.allProjects.observe(this, Observer { item ->
            item?.let {
                adapter.submitList(item)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_list_project, container, false)

        recyclerView = rootView.findViewById(R.id.project_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        val tracker = SelectionTracker.Builder<Long>(
            "projectSelection",
            recyclerView,
            TaskKeyProvider(),
            ProjectDetailsLookup(recyclerView),
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
                        for(project in projects) {
                            model.delete(project)
                        }
                        mode.finish()
                        return true
                    }
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                actionMode = null
                projects.clear()
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
                        projects.add(adapter.getItemAtPosition(key.toInt()))
                    else
                        projects.remove(adapter.getItemAtPosition(key.toInt()))
                }
            })

        adapter.tracker = tracker

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == MainActivity.EDIT_PROJECT_REQUEST) {
            val project : Project = data?.getSerializableExtra("project") as Project
            model.update(project)
        }
    }
}