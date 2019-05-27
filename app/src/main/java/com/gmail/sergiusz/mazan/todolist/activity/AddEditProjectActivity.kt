package com.gmail.sergiusz.mazan.todolist.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.gmail.sergiusz.mazan.todolist.dao.Project
import com.gmail.sergiusz.mazan.todolist.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddEditProjectActivity : AppCompatActivity() {

    private lateinit var project : Project
    private lateinit var descriptionText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)
        setSupportActionBar(findViewById(R.id.add_project_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dateText : EditText = findViewById(R.id.projectDeadline)
        val cancelTime : ImageView = findViewById(R.id.cancelDeadline)
        descriptionText = findViewById(R.id.projectTitle)

        title = getString(R.string.add_project_title)
        project = Project("", LocalDate.now())

        updateProjectDate()

        dateText.setOnClickListener {
            val onDateSetListener = { view : DatePicker, year : Int, month : Int, dayOfMonth : Int ->
                project.deadline = LocalDate.of(year, month+1, dayOfMonth)
                updateProjectDate()
            }

            val nonNullDate = project.deadline ?: LocalDate.now()
            DatePickerDialog(this@AddEditProjectActivity, onDateSetListener, nonNullDate.year,
                nonNullDate.monthValue-1, nonNullDate.dayOfMonth).show()
        }

        cancelTime.setOnClickListener {
            project.deadline = null
            updateProjectDate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId) {
        R.id.submit_action -> {
            if(descriptionText.text == null || descriptionText.text.toString() == "")
                Toast.makeText(this@AddEditProjectActivity, getString(R.string.null_title),
                    Toast.LENGTH_LONG).show()
            else {
                project.name = descriptionText.text.toString()
                intent.putExtra("project", project)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun updateProjectDate() {
        val dateText : EditText = findViewById(R.id.projectDeadline)
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yy")
        dateText.setText(project.deadline?.format(formatter))
    }
}