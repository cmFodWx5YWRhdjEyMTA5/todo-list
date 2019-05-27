package com.gmail.sergiusz.mazan.todolist.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.sergiusz.mazan.todolist.R
import com.gmail.sergiusz.mazan.todolist.adapter.TaskPagerAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager>(R.id.viewpager)
        val viewAdapter = TaskPagerAdapter(view.context, childFragmentManager)
        viewPager.adapter = viewAdapter

        val taskTab = view.findViewById<TabLayout>(R.id.task_tab)
        taskTab.setupWithViewPager(viewPager)
    }
}