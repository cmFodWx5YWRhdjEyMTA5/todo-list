package com.gmail.sergiusz.mazan.todolist

import androidx.recyclerview.selection.ItemKeyProvider

class TaskKeyProvider : ItemKeyProvider<Long>(SCOPE_MAPPED) {
    override fun getKey(position: Int): Long? = position.toLong()

    override fun getPosition(key: Long): Int = key.toInt()
}