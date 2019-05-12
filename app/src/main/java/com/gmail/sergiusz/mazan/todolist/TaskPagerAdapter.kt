package com.gmail.sergiusz.mazan.todolist

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TaskPagerAdapter(private val context : Context, manager : FragmentManager) : FragmentPagerAdapter(manager) {

    private val tabAmount : Int = 3

    override fun getItem(index: Int): Fragment = when(index) {
        0 -> TodayTaskListFragment()
        1 -> TomorrowTaskListFragment()
        else -> OverdueTaskListFragment()
    }

    override fun getCount(): Int {
        return tabAmount
    }

    override fun getPageTitle(position: Int): CharSequence? = when(position) {
        0 -> context.getString(R.string.today)
        1 -> context.getString(R.string.tomorrow)
        else -> context.getString(R.string.overdue)
    }
}