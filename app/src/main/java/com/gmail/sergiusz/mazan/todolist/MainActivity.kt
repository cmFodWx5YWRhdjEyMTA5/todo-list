package com.gmail.sergiusz.mazan.todolist

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_TASK_REQUEST = 1
        const val EDIT_TASK_REQUEST = 2
    }

    private lateinit var viewModel : TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar))

        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        val viewAdapter = TaskPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = viewAdapter

        val taskTab = findViewById<TabLayout>(R.id.task_tab)
        taskTab.setupWithViewPager(viewPager)

        viewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId) {
        R.id.add_action -> {
            intent = Intent(this, AddEditTaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST)
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            val task : Task = data?.getSerializableExtra("task") as Task
            if(requestCode == ADD_TASK_REQUEST) {
                viewModel.insert(task)
            }
        }
    }
}
