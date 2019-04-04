package com.gmail.sergiusz.mazan.todolist

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val GET_EVENT_REQUEST = 1

    private lateinit var repository : EventRepository

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar))
        supportActionBar?.title = resources.getString(R.string.today)

        repository = EventRepository(EventDatabase.getDatabase(this).eventDao())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId) {
        R.id.add_action -> {
            intent = Intent(this, AddEventActivity::class.java)
            startActivityForResult(intent, GET_EVENT_REQUEST)
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == GET_EVENT_REQUEST && resultCode == Activity.RESULT_OK) {
            val event : Event = data?.getSerializableExtra("EVENT") as Event
            scope.launch(Dispatchers.IO) {
                repository.insert(event)
                this@MainActivity.runOnUiThread {
                    Toast.makeText(this@MainActivity, getString(R.string.event_added), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
