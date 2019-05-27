package com.gmail.sergiusz.mazan.todolist.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.gmail.sergiusz.mazan.todolist.R
import com.gmail.sergiusz.mazan.todolist.dao.Task
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var task : Task
    private lateinit var descriptionText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        setSupportActionBar(findViewById(R.id.add_task_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dateText : EditText = findViewById(R.id.taskDate)
        val timeText : EditText = findViewById(R.id.taskTime)
        val cancelTime : ImageView = findViewById(R.id.cancelTime)
        descriptionText = findViewById(R.id.taskDescription)

        if(intent.hasExtra("taskToEdit")) {
            title = getString(R.string.edit_task_title)
            task = intent.getSerializableExtra("taskToEdit") as Task
            descriptionText.setText(task.description)
        }
        else {
            title = getString(R.string.add_task_title)
            var projectId : Long? = intent.getLongExtra("projectId", -1)
            projectId = if(projectId == -1L) null else projectId
            task = Task("", LocalDate.now(), null, 1, false, projectId)
        }

        updateTaskDate()
        updateTaskTime()

        dateText.setOnClickListener {
            val onDateSetListener = { view : DatePicker, year : Int, month : Int, dayOfMonth : Int ->
                task.date = LocalDate.of(year, month+1, dayOfMonth)
                updateTaskDate()
            }

            DatePickerDialog(this@AddEditTaskActivity, onDateSetListener, task.date.year,
                task.date.monthValue-1, task.date.dayOfMonth).show()
        }

        timeText.setOnClickListener {
            val onTimeSetListener = { view : TimePicker, hourOfDay : Int, minute : Int ->
                task.time = LocalTime.of(hourOfDay, minute)
                updateTaskTime()
            }

            val nonNullTime = task.time ?: LocalTime.now()
            TimePickerDialog(this@AddEditTaskActivity, onTimeSetListener,
                nonNullTime.hour, nonNullTime.minute, true).show()
        }

        val numberPicker = findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.minValue = 1
        numberPicker.maxValue = 5
        numberPicker.value = task.priority

        numberPicker.setOnValueChangedListener { np: NumberPicker, old: Int, new: Int ->
            task.priority = new
        }

        cancelTime.setOnClickListener {
            task.time = null
            updateTaskTime()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId) {
        R.id.submit_action -> {
            if(descriptionText.text == null || descriptionText.text.toString() == "")
                Toast.makeText(this@AddEditTaskActivity, getString(R.string.null_description),
                    Toast.LENGTH_LONG).show()
            else {
                task.description = descriptionText.text.toString()
                intent.putExtra("task", task)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun updateTaskDate() {
        val dateText : EditText = findViewById(R.id.taskDate)
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yy")
        dateText.setText(task.date.format(formatter))
    }

    private fun updateTaskTime() {
        val timeText : EditText = findViewById(R.id.taskTime)
        if(task.time != null) {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            timeText.setText(task.time?.format(formatter))
        } else
            timeText.text = null
    }
}