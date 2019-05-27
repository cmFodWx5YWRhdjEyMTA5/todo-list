package com.gmail.sergiusz.mazan.todolist

import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import com.gmail.sergiusz.mazan.todolist.adapter.ProjectListAdapter

class ProjectDetailsLookup(private val recyclerView : RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            val viewHolder = recyclerView.getChildViewHolder(view)
            if (viewHolder is ProjectListAdapter.ProjectViewHolder) {
                return viewHolder.getItemDetails()
            }
        }
        return null
    }
}