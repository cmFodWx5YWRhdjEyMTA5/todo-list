package com.gmail.sergiusz.mazan.todolist

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.*

class AddEventActivity : AppCompatActivity() {

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        setSupportActionBar(findViewById(R.id.add_event_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dateText : EditText = findViewById(R.id.eventDate)

        dateText.setOnClickListener {
            val onDateSetListener = { view : DatePicker, year : Int, month : Int, dayOfMonth : Int ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateEventDate()
            }

            DatePickerDialog(this@AddEventActivity, onDateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val timeText : EditText = findViewById(R.id.eventTime)

        timeText.setOnClickListener {
            val onTimeSetListener = { view : TimePicker, hourOfDay : Int, minute : Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateEventTime()
            }

            TimePickerDialog(this@AddEventActivity, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }
    }

    private fun updateEventDate() {
        val dateText : EditText = findViewById(R.id.eventDate)
        val simpleDateFormat = SimpleDateFormat("MM/dd/yy", Locale.US)
        dateText.setText(simpleDateFormat.format(calendar.time))
    }

    private fun updateEventTime() {
        val timeText : EditText = findViewById(R.id.eventTime)
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.US)
        timeText.setText(simpleDateFormat.format(calendar.time))
    }
}