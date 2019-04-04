package com.gmail.sergiusz.mazan.todolist

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AddEventActivity : AppCompatActivity() {

    private var date : LocalDate = LocalDate.now()
    private var time : LocalTime? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        setSupportActionBar(findViewById(R.id.add_event_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val addButton : Button = findViewById(R.id.addButton)
        val dateText : EditText = findViewById(R.id.eventDate)
        val timeText : EditText = findViewById(R.id.eventTime)
        val descriptionText : EditText = findViewById(R.id.eventDescription)

        updateEventDate()

        dateText.setOnClickListener {
            val onDateSetListener = { view : DatePicker, year : Int, month : Int, dayOfMonth : Int ->
                date = LocalDate.of(year, month, dayOfMonth)
                updateEventDate()
            }

            DatePickerDialog(this@AddEventActivity, onDateSetListener, date.year, date.monthValue,
                date.dayOfMonth).show()
        }

        timeText.setOnClickListener {
            val onTimeSetListener = { view : TimePicker, hourOfDay : Int, minute : Int ->
                time = LocalTime.of(hourOfDay, minute)
                updateEventTime()
            }

            val nonNullTime = time ?: LocalTime.now()
            TimePickerDialog(this@AddEventActivity, onTimeSetListener,
                nonNullTime.hour, nonNullTime.minute, true).show()
        }

        addButton.setOnClickListener {
            if(descriptionText.text == null || descriptionText.text.toString() == "")
                Toast.makeText(this@AddEventActivity, getString(R.string.null_description),
                    Toast.LENGTH_LONG).show()
            else {
                val event = Event(descriptionText.text.toString(), date, time)
                val intent = Intent()
                intent.putExtra("EVENT", event)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun updateEventDate() {
        val dateText : EditText = findViewById(R.id.eventDate)
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yy")
        dateText.setText(date.format(formatter))
    }

    private fun updateEventTime() {
        val timeText : EditText = findViewById(R.id.eventTime)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        timeText.setText(time?.format(formatter))
    }
}