package com.gmail.sergiusz.mazan.todolist

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AddTaskActivity : AppCompatActivity() {

    private var date : LocalDate = LocalDate.now()
    private var time : LocalTime? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        setSupportActionBar(findViewById(R.id.add_task_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val addButton : Button = findViewById(R.id.addButton)
        val dateText : EditText = findViewById(R.id.taskDate)
        val timeText : EditText = findViewById(R.id.taskTime)
        val descriptionText : EditText = findViewById(R.id.taskDescription)

        updateTaskDate()

        dateText.setOnClickListener {
            val onDateSetListener = { view : DatePicker, year : Int, month : Int, dayOfMonth : Int ->
                date = LocalDate.of(year, month, dayOfMonth)
                updateTaskDate()
            }

            DatePickerDialog(this@AddTaskActivity, onDateSetListener, date.year, date.monthValue,
                date.dayOfMonth).show()
        }

        timeText.setOnClickListener {
            val onTimeSetListener = { view : TimePicker, hourOfDay : Int, minute : Int ->
                time = LocalTime.of(hourOfDay, minute)
                updateTaskTime()
            }

            val nonNullTime = time ?: LocalTime.now()
            TimePickerDialog(this@AddTaskActivity, onTimeSetListener,
                nonNullTime.hour, nonNullTime.minute, true).show()
        }

        addButton.setOnClickListener {
            if(descriptionText.text == null || descriptionText.text.toString() == "")
                Toast.makeText(this@AddTaskActivity, getString(R.string.null_description),
                    Toast.LENGTH_LONG).show()
            else {
                val task = Task(descriptionText.text.toString(), date, time)
                val intent = Intent()
                intent.putExtra("task", task)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun updateTaskDate() {
        val dateText : EditText = findViewById(R.id.taskDate)
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yy")
        dateText.setText(date.format(formatter))
    }

    private fun updateTaskTime() {
        val timeText : EditText = findViewById(R.id.taskTime)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        timeText.setText(time?.format(formatter))
    }
}