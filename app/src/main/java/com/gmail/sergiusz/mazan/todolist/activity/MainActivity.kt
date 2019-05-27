package com.gmail.sergiusz.mazan.todolist.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.AdapterView
import android.widget.ExpandableListView
import android.widget.ListView
import android.widget.TextView
import com.gmail.sergiusz.mazan.todolist.*
import com.gmail.sergiusz.mazan.todolist.adapter.ExpandableListAdapter
import com.gmail.sergiusz.mazan.todolist.adapter.ListAdapter
import com.gmail.sergiusz.mazan.todolist.dao.Project
import com.gmail.sergiusz.mazan.todolist.dao.Task
import com.gmail.sergiusz.mazan.todolist.fragment.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_TASK_REQUEST = 1
        const val EDIT_TASK_REQUEST = 2
        const val ADD_PROJECT_REQUEST = 3
    }

    private lateinit var viewModel : TaskViewModel
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private var projectId : Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareToolbarAndDrawer()

        prepareInitFragment()

        viewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        prepareAddButton()

        prepareExpandableListView()

        prepareListView()

    }

    private fun prepareListView() {
        val items = listOf(
            ListAdapter.ListViewItem(
                "Home",
                R.drawable.ic_home_grey_24dp
            ),
            ListAdapter.ListViewItem(
                "All Tasks",
                R.drawable.ic_all_inclusive_grey_24dp
            ),
            ListAdapter.ListViewItem(
                "Done",
                R.drawable.ic_done_all_green_24dp
            ),
            ListAdapter.ListViewItem(
                "Add project",
                R.drawable.ic_add_24dp
            )
        )

        val listView = findViewById<ListView>(R.id.list_menu)
        listView.adapter = ListAdapter(this, items)
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if(position != 3) {
                val fragment : Fragment
                when(position) {
                    0 -> {
                        fragment = HomeFragment()
                        setTitle(R.string.app_name)
                    }
                    1 -> {
                        fragment = AllTaskListFragment()
                        setTitle(R.string.all_tasks)
                    }
                    else -> {
                        fragment = AllDoneTaskListFragment()
                        setTitle(R.string.done)
                    }
                }
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.commit()
                drawer.closeDrawers()
                projectId = null
            }
            else {
                intent = Intent(this@MainActivity, AddEditProjectActivity::class.java)
                startActivityForResult(intent,
                    ADD_PROJECT_REQUEST
                )
            }
        }
    }

    private fun prepareExpandableListView() {
        val expandableListAdapter = ExpandableListAdapter(this)
        viewModel.allProjects.observe(this, Observer { item ->
            item?.let {
                expandableListAdapter.setList(item)
            }
        })

        val expandableListView = findViewById<ExpandableListView>(R.id.expanded_menu)
        expandableListView.setAdapter(expandableListAdapter)

        expandableListView.setOnChildClickListener {
                parent, view, groupPosition, childPosition, id ->
            Log.d("MainActivity", id.toString())
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, ProjectFragment.newInstance(id))
            transaction.commit()
            title = view.findViewById<TextView>(R.id.listChild).text
            drawer.closeDrawers()
            projectId = id
            true
        }
    }

    private fun prepareAddButton() {
        val addButton = findViewById<FloatingActionButton>(R.id.add_button)
        addButton.setOnClickListener {
            if(projectId != null) {
                intent = Intent(this, AddEditProjectTaskActivity::class.java)
                intent.putExtra("projectId", projectId!!)
                startActivityForResult(intent,
                    ADD_TASK_REQUEST
                )
            } else {
                intent = Intent(this, AddEditTaskActivity::class.java)
                startActivityForResult(intent,
                    ADD_TASK_REQUEST
                )
            }
        }

    }

    private fun prepareToolbarAndDrawer() {
        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun prepareInitFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, HomeFragment())
        transaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == ADD_TASK_REQUEST) {
            val task : Task = data?.getSerializableExtra("task") as Task
            viewModel.insert(task)
        }
        if(resultCode == Activity.RESULT_OK && requestCode == ADD_PROJECT_REQUEST) {
            val project : Project = data?.getSerializableExtra("project") as Project
            viewModel.insert(project)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

}
