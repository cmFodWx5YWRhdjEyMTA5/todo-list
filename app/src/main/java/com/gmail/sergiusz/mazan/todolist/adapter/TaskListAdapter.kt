package com.gmail.sergiusz.mazan.todolist.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import com.gmail.sergiusz.mazan.todolist.R
import com.gmail.sergiusz.mazan.todolist.TaskDiffCallback
import com.gmail.sergiusz.mazan.todolist.dao.Task
import java.time.format.DateTimeFormatter

class TaskListAdapter(val listener : TaskItemClickListener, val isDateVisible : Boolean)
    : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskDiffCallback()) {

    var tracker: SelectionTracker<Long>? = null

    inner class TaskViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val descriptionTextView = view.findViewById(R.id.description_of_item) as TextView
        val timeTextView = view.findViewById(R.id.time_of_item) as TextView
        val priorityImageView = view.findViewById(R.id.priority_image_view) as ImageView
        val dateTextView = view.findViewById(R.id.date_of_item) as TextView

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            listener.onItemClick(getItem(adapterPosition), view)
        }

        fun getItemDetails() : ItemDetailsLookup.ItemDetails<Long> = object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getSelectionKey(): Long? = adapterPosition.toLong()

            override fun getPosition(): Int = adapterPosition

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, index: Int) {
        holder.descriptionTextView.text = getItem(index).description
        holder.timeTextView.text = getItem(index).time?.format(DateTimeFormatter.ofPattern("HH:mm"))
        if(isDateVisible)
            holder.dateTextView.text = getItem(index).date?.format(DateTimeFormatter.ofPattern("dd.MM.yy"))
        else
            holder.dateTextView.visibility = View.GONE

        when(getItem(index).priority) {
            1 -> holder.priorityImageView.setImageResource(R.drawable.ic_flag_willow_green_24dp)
            2 -> holder.priorityImageView.setImageResource(R.drawable.ic_flag_green_24dp)
            3 -> holder.priorityImageView.setImageResource(R.drawable.ic_flag_yellow_24dp)
            4 -> holder.priorityImageView.setImageResource(R.drawable.ic_flag_orange_24dp)
            5 -> holder.priorityImageView.setImageResource(R.drawable.ic_flag_red_24dp)
        }

        tracker?.let {
            holder.itemView.isActivated = it.isSelected(index.toLong())
        }
    }

    fun getItemAtPosition(position : Int) = getItem(position)

    interface TaskItemClickListener {
        fun onItemClick(task : Task, view : View) : Boolean
    }
}