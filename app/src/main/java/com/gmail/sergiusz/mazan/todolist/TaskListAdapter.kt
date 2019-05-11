package com.gmail.sergiusz.mazan.todolist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.time.format.DateTimeFormatter

class TaskListAdapter(val listener : TaskItemClickListener) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

        init {
            view.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View) : Boolean {
            return listener.onItemClick(tasks[adapterPosition], v)
        }

        val descriptionTextView = view.findViewById(R.id.description_of_item) as TextView
        val timeTextView = view.findViewById(R.id.time_of_item) as TextView
    }

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

    interface TaskItemClickListener {
        fun onItemClick(task : Task, view : View) : Boolean
    }
}