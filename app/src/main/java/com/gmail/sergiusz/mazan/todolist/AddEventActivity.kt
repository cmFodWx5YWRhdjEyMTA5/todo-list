package com.gmail.sergiusz.mazan.todolist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class AddEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        setSupportActionBar(findViewById(R.id.add_event_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}