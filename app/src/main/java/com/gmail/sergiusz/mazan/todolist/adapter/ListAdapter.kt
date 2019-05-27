package com.gmail.sergiusz.mazan.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.gmail.sergiusz.mazan.todolist.R

class ListAdapter(context : Context, private val items : List<ListViewItem>)
    : ArrayAdapter<ListAdapter.ListViewItem>(context, 0, items) {

    data class ListViewItem(val title : String, val drawableId : Int)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = items[position]
        val view : View = convertView ?: LayoutInflater.from(context).inflate(R.layout.listview_item, null)
        val textView = view.findViewById<TextView>(R.id.listViewItemText)
        textView.text = item.title
        textView.setCompoundDrawables(context.getDrawable(item.drawableId), null, null, null)
        return view
    }
}