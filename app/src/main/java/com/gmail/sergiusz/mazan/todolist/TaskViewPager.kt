package com.gmail.sergiusz.mazan.todolist

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class TaskViewPager : ViewPager {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}