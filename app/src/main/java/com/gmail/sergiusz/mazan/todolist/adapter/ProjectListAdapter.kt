package com.gmail.sergiusz.mazan.todolist.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import com.gmail.sergiusz.mazan.todolist.ProjectDiffCallback
import com.gmail.sergiusz.mazan.todolist.R
import com.gmail.sergiusz.mazan.todolist.dao.Project
import java.time.format.DateTimeFormatter

class ProjectListAdapter(val listener : ProjectItemClickListener)
    : ListAdapter<Project, ProjectListAdapter.ProjectViewHolder>(ProjectDiffCallback()) {

    var tracker: SelectionTracker<Long>? = null

    inner class ProjectViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val titleTextView = view.findViewById(R.id.projectTitle) as TextView
        val deadlineTextView = view.findViewById(R.id.projectDeadline) as TextView

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

    override fun onCreateViewHolder(parent: ViewGroup, index: Int): ProjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_project_list, parent, false)
        return ProjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, index: Int) {
        holder.titleTextView.text = getItem(index).name
        holder.deadlineTextView.text = getItem(index).deadline?.format(DateTimeFormatter.ofPattern("dd.MM.yy"))

        tracker?.let {
            holder.itemView.isActivated = it.isSelected(index.toLong())
        }
    }

    fun getItemAtPosition(position : Int) = getItem(position)

    interface ProjectItemClickListener {
        fun onItemClick(project : Project, view : View) : Boolean
    }
}