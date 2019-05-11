package com.gmail.sergiusz.mazan.todolist

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v4.content.ContextCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.time.format.DateTimeFormatter

class TaskListAdapter(diffCallback : DiffUtil.ItemCallback<Task>, val listener : TaskItemClickListener)
    : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(diffCallback) {

    inner class TaskViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

        val descriptionTextView = view.findViewById(R.id.description_of_item) as TextView
        val timeTextView = view.findViewById(R.id.time_of_item) as TextView
        val priorityImageView = view.findViewById(R.id.priority_image_view) as ImageView

        init {
            view.setOnLongClickListener(this)
        }

        override fun onLongClick(view: View) : Boolean {
            return listener.onItemClick(getItem(adapterPosition), view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, index: Int) {
        holder.descriptionTextView.text = getItem(index).description
        holder.timeTextView.text = getItem(index).time?.format(DateTimeFormatter.ofPattern("HH:mm"))
        when(getItem(index).priority) {
            1 -> holder.priorityImageView.setImageResource(R.drawable.ic_flag_willow_green_24dp)
            2 -> holder.priorityImageView.setImageResource(R.drawable.ic_flag_green_24dp)
            3 -> holder.priorityImageView.setImageResource(R.drawable.ic_flag_yellow_24dp)
            4 -> holder.priorityImageView.setImageResource(R.drawable.ic_flag_orange_24dp)
            5 -> holder.priorityImageView.setImageResource(R.drawable.ic_flag_red_24dp)
        }
    }

    fun getItemAtPosition(position : Int) = getItem(position)

    interface TaskItemClickListener {
        fun onItemClick(task : Task, view : View) : Boolean
    }
}