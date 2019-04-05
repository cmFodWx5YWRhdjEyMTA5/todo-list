package com.gmail.sergiusz.mazan.todolist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class TaskViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val descriptionTextView = view.findViewById(R.id.description_of_item) as TextView
}