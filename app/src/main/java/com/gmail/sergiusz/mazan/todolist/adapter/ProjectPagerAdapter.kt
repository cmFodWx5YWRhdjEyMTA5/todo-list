package com.gmail.sergiusz.mazan.todolist.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gmail.sergiusz.mazan.todolist.R
import com.gmail.sergiusz.mazan.todolist.fragment.DoneProjectTaskListFragment
import com.gmail.sergiusz.mazan.todolist.fragment.UndoneProjectTaskListFragment

class ProjectPagerAdapter(private val context : Context, manager : FragmentManager, private val projectId : Long)
    : FragmentPagerAdapter(manager) {

    private val tabAmount : Int = 2

    override fun getItem(index: Int): Fragment = when(index) {
        0 -> UndoneProjectTaskListFragment.newInstance(projectId)
        else -> DoneProjectTaskListFragment.newInstance(projectId)
    }

    override fun getCount(): Int {
        return tabAmount
    }

    override fun getPageTitle(position: Int): CharSequence? = when(position) {
        0 -> context.getString(R.string.todo)
        else -> context.getString(R.string.done)
    }
}