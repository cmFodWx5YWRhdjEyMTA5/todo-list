package com.gmail.sergiusz.mazan.todolist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.time.format.DateTimeFormatter

class TaskListAdapter : RecyclerView.Adapter<TaskViewHolder>() {

    private var tasks = emptyList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, index: Int) {
        holder.descriptionTextView.text = tasks[index].description
        holder.timeTextView.text = tasks[index].time?.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    fun setTasks(tasks : List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}