package com.gmail.sergiusz.mazan.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.gmail.sergiusz.mazan.todolist.R
import com.gmail.sergiusz.mazan.todolist.dao.Project

class ExpandableListAdapter(private val context : Context) : BaseExpandableListAdapter() {

    private var childList : List<Project> = emptyList()

    fun setList(projects : List<Project>) {
        childList = projects
        notifyDataSetChanged()
    }

    override fun getGroup(groupPosition: Int): String = "projects"

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view : View = convertView ?: LayoutInflater.from(context).inflate(R.layout.expand_list_group, null)
        val title = view.findViewById<TextView>(R.id.listGroup)
        title.text = context.getString(R.string.projects)
        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int = childList.size

    override fun getChild(groupPosition: Int, childPosition: Int): Project = childList[childPosition]

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?,
        parent: ViewGroup?): View {
        val view : View = convertView ?: LayoutInflater.from(context).inflate(R.layout.expand_list_child, null)
        val title = view.findViewById<TextView>(R.id.listChild)
        title.text = childList.get(childPosition).name
        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childList[childPosition].id

    override fun getGroupCount(): Int = 1

}
