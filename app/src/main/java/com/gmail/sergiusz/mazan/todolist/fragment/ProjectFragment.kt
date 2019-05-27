package com.gmail.sergiusz.mazan.todolist.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.sergiusz.mazan.todolist.ProjectPagerAdapter
import com.gmail.sergiusz.mazan.todolist.R

class ProjectFragment : Fragment() {

    companion object {
        fun newInstance(projectId : Long) : ProjectFragment {

            val fragment = ProjectFragment()
            val bundle = Bundle()
            bundle.putLong("projectId", projectId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager>(R.id.viewpager)
        val viewAdapter = ProjectPagerAdapter(view.context, childFragmentManager,
            arguments!!.getLong("projectId"))
        viewPager.adapter = viewAdapter

        val taskTab = view.findViewById<TabLayout>(R.id.task_tab)
        taskTab.setupWithViewPager(viewPager)
    }
}