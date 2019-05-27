package com.gmail.sergiusz.mazan.todolist.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.gmail.sergiusz.mazan.todolist.R

abstract class UndoneTaskListFragment : TaskListFragment() {

    override fun getCallback(): ItemTouchHelper.Callback {
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val taskToRemove = adapter.getItemAtPosition(viewHolder.adapterPosition)
                taskToRemove.isDone = true
                model.update(taskToRemove)

                val snackbar = Snackbar.make(viewHolder.itemView.rootView,
                    "Task was checked as done", Snackbar.LENGTH_LONG)
                snackbar.setAction("Undo") {
                    taskToRemove.isDone = false
                    model.update(taskToRemove)
                }

                snackbar.show()
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                val itemView = viewHolder.itemView
                val background = ColorDrawable(Color.parseColor("#008577"))
                background.setBounds(0, itemView.top,itemView.left + dX.toInt(), itemView.bottom)
                background.draw(c)

                val icon = ContextCompat.getDrawable(viewHolder.itemView.context,
                    R.drawable.ic_check_circle_white_32dp
                )

                val iconTop = itemView.top + (itemView.height - icon!!.intrinsicHeight) / 2
                val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
                val iconLeft = itemView.left + iconMargin
                val iconRight = itemView.left + icon.intrinsicWidth + iconMargin
                val iconBottom = iconTop + icon.intrinsicHeight

                icon.setBounds(iconLeft, iconTop,iconRight, iconBottom)
                icon.draw(c)
            }

        }
    }
}